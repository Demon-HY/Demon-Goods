//package org.demon.web.filter;
//
//import org.demon.utils.JsonUtil;
//import org.demon.starter.utils.ClientResult;
//import org.demon.starter.utils.RetCodeEnum;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.stereotype.Service;
//import org.springframework.starter.context.support.WebApplicationContextUtils;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * 登录拦截
// */
//@Service
//public class AuthFilter implements Filter {
//
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Override
//    public void init(FilterConfig filterConfig) {
//        ServletContext servletContext = filterConfig.getServletContext();
//        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
////        testApi = ctx.getBean(TestApiImpl.class);
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json; charset=utf-8");
//        // 解决跨域问题
//        String originHeader = request.getHeader("Origin");
//        response.setHeader("Access-Control-Allow-Origin", originHeader);
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
//
//        RetCodeEnum retCodeEnum = handler(request);;
////        try {
////            retCodeEnum = handler(request);
////        } catch (Exception e) {
////            logger.warn("");
////            e.printStackTrace();
////        }
//
//        if (retCodeEnum.equals(RetCodeEnum.OK)) {
//            filterChain.doFilter(servletRequest, servletResponse);
//            return;
//        }
//
//        try {
//            JsonUtil.sendJsonResponse(response, ClientResult.error(retCodeEnum));
//        } catch (Exception e) {
//            logger.warn(RetCodeEnum.ERR_SERVER_EXCEPTION.message, e);
//        }
//    }
//
//    private RetCodeEnum handler(HttpServletRequest request) {
//        return RetCodeEnum.OK;
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
