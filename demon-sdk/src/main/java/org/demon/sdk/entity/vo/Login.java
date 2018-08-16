package org.demon.sdk.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.demon.sdk.entity.Token;
import org.demon.sdk.entity.User;

import java.io.Serializable;

/**
 * 登录信息包括 Token 以及 User
 *
 * @author Demon
 * @see Token
 * @see User
 */
@Data
@ApiModel(value = "登录信息包括 Token 以及 User")
public class Login implements Serializable {

    private static final long serialVersionUID = 81196257094824082L;
    @ApiModelProperty(value = "Token信息对象")
    public Token token;

    @ApiModelProperty(value = "用户信息对象")
    public User user;

    public Login() {
    }

    public Login(Token token, User user) {
        this.token = token;
        this.user = user;
    }
}
