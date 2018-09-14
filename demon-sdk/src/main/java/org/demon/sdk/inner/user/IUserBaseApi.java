package org.demon.sdk.inner.user;

import org.demon.sdk.model.entity.User;
import org.demon.sdk.model.dto.create.UserCreateDto;
import org.demon.sdk.environment.Env;

/**
 * 用户基础操作接口
 */
public interface IUserBaseApi {

    /**
     * 创建用户
     *
     * @param userCreateDto
     * @return
     */
    User createUser(Env env, UserCreateDto userCreateDto) throws Exception;

    /**
     * 清除密码
     *
     * @param user 用户信息
     * @return
     */
    User clearPassword(User user);
}
