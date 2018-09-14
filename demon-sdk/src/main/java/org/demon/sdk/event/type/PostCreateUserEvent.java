package org.demon.sdk.event.type;

import org.demon.sdk.model.entity.User;
import org.demon.sdk.environment.Env;
import org.demon.sdk.event.Event;

/**
 * 创建用户后事件
 */
public class PostCreateUserEvent extends Event {

    private static final long serialVersionUID = 6547813944157417912L;

    /**
     * 事件类型
     */
    public static final String EVENT_TYPE = "POST_CREATE_USER_EVENT";

    public Env env;

    public User user;

    /**
     * 事件构造函数
     *
     * @param env  上下文
     * @param user 用户信息
     */
    public PostCreateUserEvent(Object source, Env env, User user) {
        super(source);
        this.env = env;
        this.user = user;
    }
}
