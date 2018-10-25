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

    public static void clearHttpReq() {
        reqLocal.remove();
    }

    public static HttpServletRequest getHttpServletRequest() {
        return reqLocal.get();
    }

    public static HttpSession getHttpSession() {
        return reqLocal.get().getSession();
    }

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
