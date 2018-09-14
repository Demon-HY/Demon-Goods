package org.demon.sdk.model.vo;

import lombok.NoArgsConstructor;
import org.demon.sdk.model.entity.Token;
import org.demon.sdk.model.entity.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录信息包括 Token 以及 User
 *
 * @author Demon
 * @see Token
 * @see User
 */
@Data
@NoArgsConstructor
@ApiModel(value = "登录信息包括 Token 以及 User")
public class LoginVo implements Serializable {

    private static final long serialVersionUID = -375888254881112710L;
    @ApiModelProperty(value = "Token信息对象")
    public Token token;

    @ApiModelProperty(value = "用户信息对象")
    public User user;

    public LoginVo(Token token, User user) {
        this.token = token;
        this.user = user;
    }
}
