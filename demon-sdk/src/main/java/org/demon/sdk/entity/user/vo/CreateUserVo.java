package org.demon.sdk.entity.user.vo;

/**
 * 创建用户需要的参数
 */
public class CreateUserVo {

    /**
     * 用户名
     */
    public String username;
    /**
     * 用户密码,加密前的
     */
    public String password;
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
