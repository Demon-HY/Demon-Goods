package org.demon.sdk.event.type;

import org.demon.sdk.entity.vo.Login;
import org.demon.sdk.environment.Env;
import org.demon.sdk.event.Event;

/**
 * 登录后事件
 */
public class PostLoginEvent extends Event {

    private static final long serialVersionUID = 6547813944157417912L;

    /**
     * 事件类型
     */
    public static final String EVENT_TYPE = "POST_LOGIN_EVENT";

    public Env env;

    public Login login;

    /**
     * 事件构造函数
     *
     * @param env   上下文
     * @param login 登录信息
     */
    public PostLoginEvent(Object source, Env env, Login login) {
        super(source);
        this.env = env;
        this.login = login;
    }
}
