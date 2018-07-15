package org.demon.module.auth;

import org.demon.sdk.entity.vo.Login;
import org.demon.starter.autoconfigure.redis.RedisApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuthRedisApi extends RedisApi {

    @Autowired
    @Qualifier("authRedisTemplate")
    private RedisTemplate redisTemplate;

    @Override
    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    /**
     * 保存登录信息
     * @param login
     * @return
     */
    public boolean saveLoginInfo(Login login) {
        return setBean(login.token.token, login, AuthConfig.DEFAULT_TOKEN_AGE);
    }

    /**
     * 获取登录信息
     * @param token 用户唯一标识
     */
    public Login getLoginInfo(String token) {
        return getBean(token, Login.class);
    }

    /**
     * 清除登录信息
     * @param token 用户唯一标识
     */
    public boolean clearLoginInfo(String token) {
        return del(token);
    }

}
