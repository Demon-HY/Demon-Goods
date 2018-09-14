package org.demon.module.auth;

import org.demon.sdk.model.vo.LoginVo;
import org.demon.starter.autoconfigure.redis.RedisApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuthRedisApi extends RedisApi {

    @Autowired
    @Qualifier("authRedisTemplate")
    public RedisTemplate redisTemplate;

    @Override
    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public AuthRedisApi(RedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    /**
     * 保存登录信息
     *
     * @param loginVo
     * @return
     */
    public boolean saveLoginInfo(LoginVo loginVo) {
        return setBean(loginVo.token.token, loginVo, AuthConfig.DEFAULT_TOKEN_AGE);
    }

    /**
     * 获取登录信息
     *
     * @param token 用户唯一标识
     */
    public LoginVo getLoginInfo(String token) {
        return getBean(token, LoginVo.class);
    }

    /**
     * 清除登录信息
     *
     * @param token 用户唯一标识
     */
    public boolean clearLoginInfo(String token) {
        return del(token);
    }
}
