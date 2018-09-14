package org.demon.sdk.inner.user;

import org.demon.sdk.model.entity.User;
import org.demon.sdk.environment.Env;

/**
 * 用户基础操作接口
 */
public interface IUserQueryApi {

    /**
     * 通过用户ID获取用户信息,屏蔽了密码
     * @param userId
     * @return
     */
    User getUser(Env env, Long userId) throws Exception;

    /**
     * 通过用户名获取用户信息
     * @param account
     * @return
     */
    User getUserByName(Env env, String account) throws Exception;

    /**
     * 验证用户状态
     * @param env
     * @param user 用户信息
     */
    void checkUserStatus(Env env, User user) throws Exception;

    /**
     * 检查密码是否符合要求
     * @param password
     * @return
     * @throws Exception
     */
    boolean checkPasswordIsLegal(String password) throws Exception;
}
