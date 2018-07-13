package org.demon.sdk.inner.user;

import org.demon.sdk.entity.user.vo.UserLoginVo;
import org.demon.sdk.entity.user.Login;
import org.demon.sdk.environment.Env;
import org.demon.sdk.exception.LogicalException;

/**
 * 登录鉴权接口
 */
public interface IAuthApi {

    /**
     * 用户登录
     * @param userLoginVo 用户登录信息
     */
    Login login(Env env, UserLoginVo userLoginVo) throws LogicalException;

    /**
     * 退出登录
     * @param env
     * @param token 字符串
     * @return true/false
     */
    boolean logout(Env env, String token);

    /**
     * 验证用户是否已登录
     * @param env
     * @param token 字符串
     * @return Login
     */
    Login checkLogin(Env env, String token);
}
