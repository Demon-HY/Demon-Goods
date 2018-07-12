package org.demon.sdk.inner.user;

import org.demon.sdk.entity.user.User;
import org.demon.sdk.entity.user.vo.CreateUserVo;

/**
 * 用户基础操作接口
 */
public interface UserBaseApi {

    /**
     * 创建用户
     * @param createUserVo
     * @return
     */
    User createUser(CreateUserVo createUserVo);
}
