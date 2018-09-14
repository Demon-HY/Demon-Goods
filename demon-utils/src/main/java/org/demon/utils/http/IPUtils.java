package org.demon.utils.http;

import org.demon.utils.ValidUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Demon-HY on 2018/1/19 0019.
 */
public class IPUtils {

    private static final String UNKNOWN = "unknown";

    /**
     * 校验 IP 的合法性<br/>
     *
     * @param ip
     * @return true/false
     */
    public static boolean checkIPVaildity(String ip) {
        // 正则表达式
        String expression = "(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)){3}";
        return checkIp(ip, expression);
    }

    /**
     * 一段匹配 String.matches
     *
     * @param ip         被检查的ip
     * @param expression 表达式
     * @return 是否匹配
     */
    public static boolean checkIp(String ip, String expression) {
        if (null != ip && null != expression) {
            return ip.matches(expression);
        }
        return false;
    }

    /**
     * 多段匹配
     *
     * @param ip         被检查的ip
     * @param expression 表达式
     * @return
     */
    public static boolean checkIpMultiSection(String ip, String expression) {
        if (null != ip && null != expression) {
            for (String exp : expression.split(",")) {
                if (checkIp(ip, exp)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 多段匹配并且检查白名单设置
     *
     * @param ip
     * @param expression
     * @param white
     * @return
     */
    public static boolean checkIpMultiSectionWithWhiteFlag(String ip, String expression, boolean white) {
        if (null != ip && null != expression) {
            if (checkIpMultiSection(ip, expression)) {
                return white;
            } else {
                return !white;
            }
        }
        return false;
    }

    /**
     * 根据String ip地址转换成为long类型
     *
     * @param ip
     * @return
     */
    public static long ip2Long(String ip) {
        if (ValidUtils.isNotBlank(ip)) {
            String[] ipArray = ip.split("\\.");
            return ((Long.valueOf(ipArray[0]) * 256 + Long.valueOf(ipArray[1])) * 256 + Long.valueOf(ipArray[2])) * 256
                    + Long.valueOf(ipArray[3]);
        } else {
            return 0L;
        }
    }

    /**
     * 根据long类型的数据转换成为String类型
     *
     * @param ip
     * @return
     */
    public static String long2String(long ip) {
        StringBuilder sb = new StringBuilder();
        long[] ipArray = new long[4];
        ipArray[0] = ip / 256 / 256 / 256;
        ipArray[1] = ip / 256 / 256 - ipArray[0] * 256;
        ipArray[2] = ip / 256 - ipArray[0] * 256 * 256 - ipArray[1] * 256;
        ipArray[3] = ip - ipArray[0] * 256 * 256 * 256 - ipArray[1] * 256 * 256 - ipArray[2] * 256;
        sb.append(ipArray[0]).append(".").append(ipArray[1])
                .append(".").append(ipArray[2]).append(".")
                .append(ipArray[3]);
        return sb.toString();
    }

//    /**
//     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
//     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值,这个方法适合于大多数情况
//     *
//     * @return ip
//     */
//    public static String getIpAddr(HttpServletRequest request) {
//        String remoteAddr = request.getHeader("X-Real-IP");
//        if (ValidUtils.isNotBlank(remoteAddr)) {
//            remoteAddr = request.getHeader("X-Forwarded-For");
//        }
//        if (ValidUtils.isNotBlank(remoteAddr)) {
//            remoteAddr = request.getHeader("Proxy-Client-IP");
//        }
//        if (ValidUtils.isNotBlank(remoteAddr)) {
//            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
//        }
//        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
//    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     *
     * @return ip
     */
    public static String getIPAddr(HttpServletRequest request) {
        String ipAddress;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if ("127.0.0.1".equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet;
                    try {
                        inet = InetAddress.getLocalHost();
                        ipAddress = inet.getHostAddress();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }

        return ipAddress;
    }
}
