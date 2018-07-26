package org.demon.sdk.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 用户登录对象
 */
@ApiModel(value = "用户登录对象")
public class UserLoginVo implements Serializable {

    private static final long serialVersionUID = -2089952341710111130L;

    public UserLoginVo() {
    }

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", position = 1)
    public String account;
    /**
     * 用户密码,加密前的
     */
    @ApiModelProperty(value = "用户密码", required = true, position = 2)
    public String password;
    /**
     * 账号类型:账号-account,手机号-phone,邮箱-email
     */
    @ApiModelProperty(value = "账号类型:账号-account,手机号-phone,邮箱-email", position = 3)
    public String type;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", position = 4)
    public Integer phone;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱", position = 5)
    public String email;
    /**
     * QQ
     */
    @ApiModelProperty(value = "QQ", position = 6)
    public String qq;
    /**
     * token 过期时间(单位：毫秒)
     */
    @ApiModelProperty(value = "token 过期时间(单位：毫秒)", position = 7)
    public Long tokenAge;
    /**
     * 是否写入cookie
     */
    @ApiModelProperty(value = "是否写入cookie", position = 8)
    public boolean isCookie;
    /**
     * 扩展属性
     */
    @ApiModelProperty(value = "扩展属性", hidden = true)
    public String exattr;

    @Override
    public String toString() {
        return "UserLoginVo{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", type='" + type + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                ", qq='" + qq + '\'' +
                ", tokenAge=" + tokenAge +
                ", isCookie=" + isCookie +
                ", exattr='" + exattr + '\'' +
                '}';
    }
}
