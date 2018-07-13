package org.demon.sdk.inner.user;

import org.demon.sdk.entity.user.User;
import org.demon.sdk.entity.user.vo.UserLoginVo;

/**
 * 用户基础操作接口
 */
public interface IUserBaseApi {

    /**
     * 创建用户
     * @param userLoginVo
     * @return
     */
    User createUser(UserLoginVo userLoginVo);
}
