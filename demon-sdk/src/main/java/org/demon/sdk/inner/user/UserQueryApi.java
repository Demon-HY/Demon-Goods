package org.demon.sdk.inner.user;

import org.demon.sdk.entity.user.User;

/**
 * 用户基础操作接口
 */
public interface UserQueryApi {

    /**
     * 通过用户ID获取用户信息,屏蔽了密码
     * @param userId
     * @return
     */
    User getUser(Long userId);

    /**
     * 通过用户名获取用户信息
     * @param userName
     * @return
     */
    User getUserByName(String userName);
}
