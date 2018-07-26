package org.demon.sdk.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 创建用户
 */
@ApiModel(value = "创建用户属性")
public class UserCreateVo implements Serializable {

    private static final long serialVersionUID = -5169678047284371631L;

    public UserCreateVo() {
    }

    // 用户名
    @ApiModelProperty(value = "用户名", required = true, position = 1)
    public String name;

    // 密码
    @ApiModelProperty(value = "密码", required = true, position = 2)
    public String password;

    // 昵称
    @ApiModelProperty(value = "昵称", position = 3)
    public String nick;

    // 手机号
    @ApiModelProperty(value = "手机号", position = 4)
    public String phone;

    // 邮箱
    @ApiModelProperty(value = "邮箱", position = 5)
    public String email;

    // QQ 号
    @ApiModelProperty(value = "QQ 号", position = 6)
    public String qq;

    @Override
    public String toString() {
        return "UserCreateVo{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", nick='" + nick + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", qq='" + qq + '\'' +
                '}';
    }
}
