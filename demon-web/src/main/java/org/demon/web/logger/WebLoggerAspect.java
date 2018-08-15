package org.demon.web.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.demon.sdk.config.SysContants;
import org.demon.utils.beans.BeanUtils;
import org.demon.utils.http.IPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web 请求信息记录
 * <p>
 * 日志格式：
 */
@Aspect
@Component
public class WebLoggerAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 定义一个切入点
     */
    @Pointcut("execution(* org.demon.web.http..*.*(..))")
    public void webLog() {
    }

    @AfterReturning(value = "webLog()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = attributes.getRequest();
        HttpServletResponse resp = attributes.getResponse();
        // 请求的唯一标识，客户端通过这个可以查询到该次请求记录
        String requestId = (String) req.getAttribute(SysContants.REQUEST_ID);
        resp.setHeader(SysContants.REQUEST_ID, requestId);

        // 处理完请求，返回内容
        logger.info("HTTP-OK  {}  {}  {}  {}  P:{}  R:{}",
                IPUtils.getIPAddr(req), requestId, req.getMethod(), getRequestUrl(req),
                joinPoint.getArgs(), getResponseBody(result));
    }

    @AfterThrowing(value = "webLog()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = attributes.getRequest();
        HttpServletResponse resp = attributes.getResponse();
        // 请求的唯一标识，客户端通过这个可以查询到该次请求记录
        String requestId = (String) req.getAttribute(SysContants.REQUEST_ID);
        resp.setHeader(SysContants.REQUEST_ID, requestId);

        // 这里可以捕获异常，但无法处理异常，异常还是会抛给 JVM

        // 处理完请求，返回内容
        logger.error("HTTP-ERROR  {}  {}  {}  {}  P:{}  E:{}",
                IPUtils.getIPAddr(req), requestId, req.getMethod(), getRequestUrl(req),
                joinPoint.getArgs(), e.getMessage(), e);
    }

    /**
     * 获取请求路径
     */
    private String getRequestUrl(HttpServletRequest req) {
        return req.getRequestURL().toString();
    }

//    /**
//     * 获取请求参数
//     */
//    private String getRequestParams(HttpServletRequest request) {
//        JSONObject paramObj = new JSONObject(); // 请求参数
//        //获取所有参数
//        Enumeration<String> enu = request.getParameterNames();
//        while (enu.hasMoreElements()) {
//            String paraName = enu.nextElement();
//            paramObj.put(paraName, request.getParameter(paraName));
//        }
//
//        return paramObj.toString();
//    }

    /**
     * 获取返回结果,截取1024个长度
     */
    private String getResponseBody(Object result) {
        String resultObj = "";
        try {
            resultObj = BeanUtils.describeStr(result);
            resultObj = resultObj.length() > 1024 ? resultObj.substring(0, 1023) : resultObj;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultObj;
    }

}

