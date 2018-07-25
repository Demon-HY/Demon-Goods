package org.demon.web.logger;

import com.alibaba.fastjson.JSONObject;
import org.demon.utils.RandomUtil;
import org.demon.utils.ValidUtils;
import org.demon.utils.beans.BeanUtils;
import org.demon.utils.http.IPUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

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
     * 记录一个全局的唯一标识，可以通过它找到请求和响应
     */
    private static final ThreadLocal<String> REQUEST_CODE = new ThreadLocal<>();

    private static void setRequestCode(String user) {
        REQUEST_CODE.set(user);
    }

    private static String getRequestCode() {
        return REQUEST_CODE.get();
    }

    private static void removeRequestCode() {
        REQUEST_CODE.remove();
    }

    /**
     * 定义一个切入点
     */
    @Pointcut("execution(* org.demon.web.http..*.*(..))")
    public void webLog() {
    }

//	@Before("webLog()")
//	public void doBefore(JoinPoint joinPoint) {
//		setRequestCode(CodeGeneratorUtil.createOrderNo(""));
//
//		// 接收到请求，记录请求内容
//		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//		HttpServletRequest request = attributes.getRequest();
//		// 记录下请求内容
//		String url = request.getRequestURI();
//		if (StringUtils.notEmpty(url) && url.contains("hello")) { // 屏蔽阿里的健康检查
//			return;
//		}
//		String httpMethod = request.getMethod();
//		JSONObject obj = new JSONObject(); // 请求参数
//		//获取所有参数
//		Enumeration<String> enu = request.getParameterNames();
//		while (enu.hasMoreElements()) {
//			String paraName = enu.nextElement();
//			obj.put(paraName, request.getParameter(paraName));
//		}
//		logger.info("{}  {}  OK  REQ  {}  {}  {}", IPUtils.getIPAddr(request), getRequestCode(), url, httpMethod, obj.toString());
//	}

    @AfterReturning(value = "webLog()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        setRequestCode(RandomUtil.getRequestId());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = attributes.getRequest();
        HttpServletResponse resp = attributes.getResponse();
        // 请求的唯一标识，客户端通过这个可以查询到该次请求记录
        resp.setHeader("RequestId", getRequestCode());

        // 处理完请求，返回内容
        logger.info("HTTP-OK  {}  {}  {}  {}  P:{}  R:{}",
                IPUtils.getIPAddr(req), getRequestCode(), req.getMethod(), getRequestUrl(req),
                joinPoint.getArgs(), getResponseBody(result));
        removeRequestCode();
    }

    @AfterThrowing(value = "webLog()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        setRequestCode(RandomUtil.getRequestId());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = attributes.getRequest();
        HttpServletResponse resp = attributes.getResponse();
        resp.setHeader("RequestId", getRequestCode());

        // 这里可以捕获异常，但无法处理异常，异常还是会抛给 JVM

        // 处理完请求，返回内容
        logger.error("HTTP-ERROR  {}  {}  {}  {}  P:{}  E:{}",
                IPUtils.getIPAddr(req), getRequestCode(), req.getMethod(), getRequestUrl(req),
                joinPoint.getArgs(), e.getMessage(), e);
        removeRequestCode();
    }

    /**
     * 获取请求路径
     */
    private String getRequestUrl(HttpServletRequest req) {
        return req.getRequestURL().toString();
    }

    /**
     * 获取请求参数
     */
    private String getRequestParams(HttpServletRequest request) {
        JSONObject paramObj = new JSONObject(); // 请求参数
        //获取所有参数
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = enu.nextElement();
            paramObj.put(paraName, request.getParameter(paraName));
        }

        return paramObj.toString();
    }

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

