package org.demon.starter.web.context;

import lombok.NoArgsConstructor;
import org.demon.starter.common.constants.SysContants;
import org.demon.utils.RandomUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@NoArgsConstructor
public class RequestContext {

    private static ThreadLocal<HttpServletRequest> reqLocal = new InheritableThreadLocal<>();

    public static void setHttpServletRequest(HttpServletRequest request) {
        reqLocal.set(request);
    }

    /**
     * 清除request线程变量。
     */
    public static void clearHttpReq() {
        reqLocal.remove();
    }

    /**
     * 获取当前请求的Request
     */
    public static HttpServletRequest getHttpServletRequest() {
        return reqLocal.get();
    }

    /**
     * 获取当前Session
     */
    public static HttpSession getHttpSession() {
        return reqLocal.get().getSession();
    }

    /**
     * 获取当前上下文的值
     */
    public static Object getAttribute(String key) {
        Object retVal = getHttpSession().getAttribute(key);
        if (null == retVal) {
            retVal = getHttpServletRequest().getAttribute(key);
        }

        return retVal;
    }

    /**
     * 获取请求ID
     */
    public static String getRequestId() {
        try {
            return (String) reqLocal.get().getAttribute(SysContants.REQUEST_ID);
        } catch (Exception e) {
            String requestId = RandomUtil.getRequestId();
            reqLocal.get().setAttribute(SysContants.REQUEST_ID, requestId);
            return requestId;
        }
    }
}
