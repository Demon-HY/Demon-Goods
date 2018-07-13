package org.demon.sdk.inner.user;

import org.demon.sdk.entity.user.User;
import org.demon.sdk.environment.Env;
import org.demon.sdk.exception.ParamException;

/**
 * 用户基础操作接口
 */
public interface IUserQueryApi {

    /**
     * 通过用户ID获取用户信息,屏蔽了密码
     * @param userId
     * @return
     */
    User getUser(Env env, Long userId) throws ParamException;

    /**
     * 通过用户名获取用户信息
     * @param account
     * @return
     */
    User getUserByName(Env env, String account) throws ParamException;

    /**
     * 验证用户状态
     * @param env
     * @param user 用户信息
     */
    void checkUserStatus(Env env, User user);
}
