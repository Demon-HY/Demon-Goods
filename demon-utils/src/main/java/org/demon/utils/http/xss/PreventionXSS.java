package org.demon.utils.http.xss;

/**
 * XSS : 跨站脚本攻击缩写
 * 防止 XSS 攻击
 * @author monitor
 *
 */
public class PreventionXSS {

    /**
     * 防止 XSS 攻击
     */
    public static String cleanXSS(String value) {
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        return value;
    }
}
