package org.demon.sdk.event.type;

import org.demon.sdk.entity.request.UserCreateVo;
import org.demon.sdk.entity.request.UserLoginVo;
import org.demon.sdk.environment.Env;
import org.demon.sdk.event.Event;

/**
 * 创建用户前事件
 */
public class PreCreateUserEvent extends Event {

    private static final long serialVersionUID = 6547813944157417912L;

    /**
     * 事件类型
     */
    public static final String EVENT_TYPE = "PRE_CREATE_USER_EVENT";

    public Env env;

    public UserCreateVo userCreateVo;

    /**
     * 事件构造函数
     *
     * @param env      上下文
     * @param userCreateVo  用户创建信息
     */
    public PreCreateUserEvent(Object source, Env env, UserCreateVo userCreateVo) {
        super(source);
        this.env = env;
        this.userCreateVo = userCreateVo;
    }
}
