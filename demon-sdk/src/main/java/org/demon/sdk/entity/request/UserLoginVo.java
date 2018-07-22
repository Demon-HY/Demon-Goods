package org.demon.sdk.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 用户登录对象
 */
@ApiModel(value = "用户登录对象")
public class UserLoginVo implements Serializable {

    private static final long serialVersionUID = 8157611022140886557L;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    public String account;
    /**
     * 用户密码,加密前的
     */
    @ApiModelProperty(value = "用户密码")
    public String password;
    /**
     * 账号类型:账号-account,手机号-phone,邮箱-email
     */
    @ApiModelProperty(value = "账号类型:账号-account,手机号-phone,邮箱-email")
    public String type;
    /**
     * token 过期时间(单位：毫秒)
     *
     */
    @ApiModelProperty(value = "token 过期时间(单位：毫秒)", hidden = true)
    public Long tokenAge;
    /**
     * 是否写入cookie
     */
    @ApiModelProperty(value = "是否写入cookie", hidden = true)
    public boolean isCookie;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    public Integer phone;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    public String email;
    /**
     * QQ
     */
    @ApiModelProperty(value = "QQ", hidden = true)
    public String qq;
    /**
     * 扩展属性
     */
    @ApiModelProperty(value = "扩展属性", hidden = true)
    public String exattr;

}
