package org.demon.sdk.environment;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.demon.sdk.model.vo.LoginVo;
import org.demon.sdk.retCode.BizRetCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 上下文环境变量,将一些请求的常用参数封装进去
 * <p>
 * Created by heyan on 2017/10/31 0031.
 */
@Data
@NoArgsConstructor
public class Env implements Serializable {

    private static final long serialVersionUID = -1L;
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
    public LoginVo loginVo;
    /**
     * 内部接口调用时跳过权限
     */
    public Boolean continueRight = false;
    /**
     * 错误信息
     */
    public BizRetCode.RetCode bizRetCode = BizRetCode.OK;
}
