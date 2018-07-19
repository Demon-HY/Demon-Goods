package org.demon.web.filter;

import org.demon.module.auth.AuthConfig;
import org.demon.module.auth.AuthRedisApi;
import org.demon.sdk.config.SysContants;
import org.demon.sdk.entity.vo.Login;
import org.demon.sdk.environment.Env;
import org.demon.sdk.utils.ClientResult;
import org.demon.sdk.utils.RetCodeEnum;
import org.demon.utils.JsonUtil;
import org.demon.utils.ValidUtils;
import org.demon.utils.http.IPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 登录拦截
 */
@Service
public class AuthFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthRedisApi authRedisApi;

    @Override
    public void init(FilterConfig filterConfig) {
        ServletContext servletContext = filterConfig.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        authRedisApi = ctx.getBean(AuthRedisApi.class);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        logger.info("AuthFilter start ...");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        // 解决跨域问题
        String[] origin = {"http://localhost:8080"};
        Set<String> allowedOrigins = new HashSet<>(Arrays.asList(origin));
        String originHeader = request.getHeader("Origin");
        if (allowedOrigins.contains(originHeader)) {
            response.setHeader("Access-Control-Allow-Origin", originHeader);
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            // 如果要把Cookie发到服务器，需要指定Access-Control-Allow-Credentials字段为true;
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("XDomainRequestAllowed", "1");
            //表明服务器支持的所有头信息字段
            response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since," +
                    "Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token, X-Device, X-Token");
        }

        // 屏蔽 /favicon.ico
        String uri = request.getRequestURI();
        if (uri.equals("/favicon.ico")) {
            return ;
        }

        RetCodeEnum retCodeEnum = handler(request, response);

        if (retCodeEnum.equals(RetCodeEnum.OK)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        try {
            JsonUtil.sendJsonResponse(response, ClientResult.error(retCodeEnum));
        } catch (Exception e) {
            logger.warn(RetCodeEnum.ERR_SERVER_EXCEPTION.message, e);
        }

        logger.info("AuthFilter end ...");
    }

    private RetCodeEnum handler(HttpServletRequest request, HttpServletResponse response) {
        // 解决 OPIONS 跨越请求
        if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {
            return RetCodeEnum.OK;
        }

        Env env = parseServlet(request, response);
        request.setAttribute("env", env);

        String uri = request.getRequestURI();

        // 匿名接口可以跳过登录
        if (uri.contains(AuthConfig.ANNO_PATH)) {
            return RetCodeEnum.OK;
        }
        // 从缓存中读取token数据
        Login login = authRedisApi.getLoginInfo(env.token);
        if (login == null) {
            return RetCodeEnum.ERR_TOKEN_NOT_FOUND;
        }

        env.login = login;

        return RetCodeEnum.OK;
    }

    /**
     * 解析请求信息
     * @param request
     * @param response
     * @return
     */
    private Env parseServlet(HttpServletRequest request, HttpServletResponse response) {
        Env env = new Env();
        env.clientIP = IPUtils.getIPAddr(request);
        String device = request.getHeader(SysContants.HEADER_DEVICE);
        if (ValidUtils.isNotBlank(device)) {
            env.device = device.toLowerCase();
        }
        env.token = request.getHeader(SysContants.HEADER_TOKEN);
        env.request = request;
        env.response = response;

        return env;
    }

    @Override
    public void destroy() {

    }
}
