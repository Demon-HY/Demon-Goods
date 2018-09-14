package org.demon.sdk.inner.user;

import org.demon.sdk.model.dto.request.UserLoginVo;
import org.demon.sdk.model.vo.LoginVo;
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
    LoginVo login(Env env, UserLoginVo userLoginVo) throws Exception;

    /**
     * 退出登录
     * @param env
     */
    boolean logout(Env env) throws LogicalException;

    /**
     * 验证用户是否已登录
     * @param env
     */
    LoginVo checkLogin(Env env) throws Exception;
}
