package org.demon.starter.common.logger;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.demon.starter.web.context.RequestContext;
import org.demon.utils.beans.BeanUtils;
import org.demon.utils.http.IPUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Web 请求信息记录
 * <p>
 * 日志格式：
 */
@Aspect
@Component
public class WebLoggerAspect extends AbstractLogClass {

    /**
     * mvc 出参打印的最大长度字符数
     */
    @Value("${server.mvc.print.return.limit:1024}")
    private Integer retStrLimit;

    /*************************************************
     * 在Controller 加日志切面，排除健康检查  {@link org.demon.starter.autoconfigure.annotion.LogIgnore}注解
     */
    @Pointcut(value = "((@within(org.springframework.web.bind.annotation.RestController))"
            + "||(@within(org.springframework.stereotype.Controller))"
            + "||(@annotation(org.springframework.web.bind.annotation.GetMapping))"
            + "||(@annotation(org.springframework.web.bind.annotation.PostMapping))"
            + "||(@annotation(org.springframework.web.bind.annotation.RequestMapping))"
            + ") && !(@within(org.demon.starter.autoconfigure.annotion.LogIgnore))")
    public void webLog() {
    }

    /**
     * 请求前记录日志
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        String url = request.getRequestURI();

        String httpMethod = request.getMethod();
        JSONObject obj = new JSONObject(); // 请求参数
        //获取所有参数
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = enu.nextElement();
            obj.put(paraName, request.getParameter(paraName));
        }

        String requestId = RequestContext.getRequestId();

        logger.info("{}  {}  OK  REQ  {}  {}  {}", IPUtils.getIPAddr(request), requestId, url, httpMethod, obj.toString());
    }

    @AfterReturning(value = "webLog()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = attributes.getRequest();
        // TODO 请求的唯一标识，客户端通过这个可以查询到该次请求记录,需要在拦截器里写入该属性
        String requestId = RequestContext.getRequestId();

        // 处理完请求，返回内容
        logger.info("HTTP-OK  {}  {}  {}  {}  P:{}  R:{}",
                IPUtils.getIPAddr(req), requestId, req.getMethod(), getRequestUrl(req),
                joinPoint.getArgs(), getResponseBody(result));
    }

    @AfterThrowing(value = "webLog()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = attributes.getRequest();
        // 请求的唯一标识，客户端通过这个可以查询到该次请求记录
        String requestId = RequestContext.getRequestId();

        // 这里可以捕获异常，但无法处理异常，异常还是会抛给 JVM

        // 处理完请求，返回内容
        logger.error("HTTP-ERROR  {}  {}  {}  {}  P:{}  E:{}",
                IPUtils.getIPAddr(req), requestId, req.getMethod(), getRequestUrl(req),
                joinPoint.getArgs(), e.getMessage(), e.getMessage());
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

