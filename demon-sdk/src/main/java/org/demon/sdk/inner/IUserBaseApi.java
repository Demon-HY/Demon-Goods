package org.demon.sdk.inner;

import org.demon.sdk.entity.User;
import org.demon.sdk.entity.request.UserCreateVo;
import org.demon.sdk.environment.Env;

/**
 * 用户基础操作接口
 */
public interface IUserBaseApi {

    /**
     * 创建用户
     *
     * @param userCreateVo
     * @return
     */
    User createUser(Env env, UserCreateVo userCreateVo) throws Exception;

    /**
     * 清除密码
     *
     * @param user 用户信息
     * @return
     */
    User clearPassword(User user);
}
