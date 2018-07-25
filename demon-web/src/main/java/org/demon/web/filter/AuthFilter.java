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
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录拦截
 */
@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/api/*", filterName = "authFilter")
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

        // 兼容IE下AJAX 跨域重定向问题
        response.setHeader("P3P", "CP=CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT " +
                "DEM STA PRE COM NAV OTC NOI DSP COR");

        RetCodeEnum retCodeEnum = handler(request, response);

        if (retCodeEnum.equals(RetCodeEnum.OK)) {
            filterChain.doFilter(servletRequest, servletResponse);
            logger.info("AuthFilter end ...");
            return;
        }

        JsonUtil.sendJsonResponse(response, ClientResult.error(retCodeEnum));
    }

    private RetCodeEnum handler(HttpServletRequest request, HttpServletResponse response) {
        Env env = parseServlet(request, response);
        request.setAttribute("env", env);

        String uri = request.getRequestURI();

        // 该拦截器只拦截接口请求
        if (!uri.contains(".do")) {
            return RetCodeEnum.OK;
        }

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
     *
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
        } else {
            env.device = "web";
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
