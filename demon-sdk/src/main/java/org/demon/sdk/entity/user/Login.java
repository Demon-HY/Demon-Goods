package org.demon.sdk.entity.user;

/**
 * 登录信息包括 Token 以及 User
 *
 * @author Demon
 * @see Token
 * @see User
 */
public class Login {

    /** token信息对象 */
    public Token token;
    /** 用户信息对象 */
    public User user;

    public Login(Token token, User user) {
        this.token = token;
        this.user = user;
    }
}
