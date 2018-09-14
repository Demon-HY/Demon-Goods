package org.demon.sdk.event.type;

import org.demon.sdk.model.dto.create.UserCreateDto;
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

    public UserCreateDto userCreateDto;

    /**
     * 事件构造函数
     *
     * @param env      上下文
     * @param userCreateDto  用户创建信息
     */
    public PreCreateUserEvent(Object source, Env env, UserCreateDto userCreateDto) {
        super(source);
        this.env = env;
        this.userCreateDto = userCreateDto;
    }
}
