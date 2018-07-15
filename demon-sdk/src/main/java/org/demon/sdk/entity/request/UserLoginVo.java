package org.demon.sdk.entity.request;

/**
 * 创建用户需要的参数
 */
public class UserLoginVo {

    /**
     * 用户名
     */
    public String account;
    /**
     * 用户密码,加密前的
     */
    public String password;
    /**
     * 账号类型:账号-account,手机号-phone,邮箱-email
     */
    public String type;
    /**
     * token 过期时间(单位：毫秒)
     *
     */
    public Long tokenAge;
    /**
     * 是否写入cookie
     */
    public boolean isCookie;
    /**
     * 手机号
     */
    public Integer phone;
    /**
     * 邮箱
     */
    public String email;
    /**
     * QQ
     */
    public String qq;
    /**
     * 扩展属性
     */
    public String exattr;

}
