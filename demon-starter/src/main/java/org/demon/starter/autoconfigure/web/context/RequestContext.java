package org.demon.starter.autoconfigure.web.context;

import lombok.NoArgsConstructor;

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
}
