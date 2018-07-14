package org.demon.sdk.event.type;

import org.demon.sdk.environment.Env;
import org.demon.sdk.event.Event;

/**
 * 退出登录后事件
 */
public class PostLogoutEvent extends Event {

    private static final long serialVersionUID = 6547813944157417912L;

    /**
     * 事件类型
     */
    public static final String EVENT_TYPE = "POST_LOGOUT_EVENT";

    public Env env;

    /**
     * 事件构造函数
     *
     * @param env 上下文
     */
    public PostLogoutEvent(Object source, Env env) {
        super(source);
        this.env = env;
    }
}
