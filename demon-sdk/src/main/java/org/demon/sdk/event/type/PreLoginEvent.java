package org.demon.sdk.event.type;

import org.demon.sdk.model.dto.request.UserLoginVo;
import org.demon.sdk.environment.Env;
import org.demon.sdk.event.Event;

/**
 * 登录前事件
 */
public class PreLoginEvent extends Event {

    private static final long serialVersionUID = 6547813944157417912L;

    /**
     * 事件类型
     */
    public static final String EVENT_TYPE = "PRE_LOGIN_EVENT";

    public Env env;

    public UserLoginVo userLoginVo;

    /**
     * 事件构造函数
     *
     * @param env      上下文
     * @param userLoginVo  用户登录信息
     */
    public PreLoginEvent(Object source, Env env, UserLoginVo userLoginVo) {
        super(source);
        this.env = env;
        this.userLoginVo = userLoginVo;
    }
}
