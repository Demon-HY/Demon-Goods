package org.demon.sdk.entity.vo;

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
public class Login implements Serializable {

    private static final long serialVersionUID = 2771644727971817874L;

    /** token信息对象 */
    public Token token;
    /** 用户信息对象 */
    public User user;

    public Login() {}

    public Login(Token token, User user) {
        this.token = token;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Login{" +
                "token=" + token +
                ", user=" + user +
                '}';
    }
}
