package org.demon.sdk.environment;

import org.demon.sdk.entity.vo.Login;
import org.demon.sdk.utils.RetCodeEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 上下文环境变量,将一些请求的常用参数封装进去
 *
 * Created by heyan on 2017/10/31 0031.
 */
public class Env implements Serializable {

    private static final long serialVersionUID = -7100054939286964751L;
    /**
     * HTTP 请求所对应的模块名称
     */
    public String moduleName;
    /**
     * Serrvlet HTTP 请求对象
     */
    public HttpServletRequest request;
    /**
     * Servlet HTTP 返回对象
     */
    public HttpServletResponse response;
    /**
     * 用户ID
     */
    public Long userId; // 用户唯一标识
    /**
     * 用户登录凭证
     */
    public String token;
    /**
     * 客户端IP
     */
    public String clientIP;
    /**
     * 客户端设备: pc/web/android/iphone
     */
    public String device;
    /**
     * 用户登录信息
     */
    public Login login;
    /**
     * 错误信息
     */
    RetCodeEnum retCodeEnum;
}
