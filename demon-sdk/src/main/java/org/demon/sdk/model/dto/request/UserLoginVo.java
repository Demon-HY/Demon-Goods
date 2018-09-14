package org.demon.sdk.model.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户登录对象
 */
@Data
@NoArgsConstructor
@ApiModel(value = "用户登录对象")
public class UserLoginVo implements Serializable {

    private static final long serialVersionUID = 7812076455904605538L;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    public String account;
    /**
     * 用户密码,加密前的
     */
    @ApiModelProperty(value = "用户密码", required = true)
    public String password;
    /**
     * 账号类型:账号-account,手机号-phone,邮箱-email
     */
    @ApiModelProperty(value = "账号类型:账号-account,手机号-phone,邮箱-email")
    public String type;
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
    @ApiModelProperty(value = "QQ")
    public String qq;
    /**
     * token 过期时间(单位：毫秒)
     */
    @ApiModelProperty(value = "token 过期时间(单位：毫秒)")
    public Long tokenAge;
    /**
     * 是否写入cookie
     */
    @ApiModelProperty(value = "是否写入cookie")
    public boolean isCookie;
    /**
     * 扩展属性
     */
    @ApiModelProperty(value = "扩展属性", hidden = true)
    public String exattr;
}
