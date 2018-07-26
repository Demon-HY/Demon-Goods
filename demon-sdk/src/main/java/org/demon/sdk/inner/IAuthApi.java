package org.demon.sdk.inner;

import org.demon.sdk.entity.request.UserLoginVo;
import org.demon.sdk.entity.vo.Login;
import org.demon.sdk.environment.Env;
import org.demon.sdk.exception.LogicalException;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

/**
 * 登录鉴权接口
 */
public interface IAuthApi {

    /**
     * 用户登录
     * @param userLoginVo 用户登录信息
     */
    Login login(Env env, UserLoginVo userLoginVo) throws Exception;

    /**
     * 退出登录
     * @param env
     * @return true/false
     */
    boolean logout(Env env) throws LogicalException;

    /**
     * 验证用户是否已登录
     * @param env
     * @return Login
     */
    Login checkLogin(Env env) throws LogicalException;
}
