package org.demon.sdk.model.dto.create;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 创建用户
 */
@Data
@ApiModel(value = "创建用户属性")
@NoArgsConstructor
public class UserCreateDto implements Serializable {

    private static final long serialVersionUID = 2809999206266210853L;
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
}
