package org.demon.module.auth;

import org.demon.sdk.entity.vo.Login;
import org.demon.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AuthRedisApi {

    @Autowired
    @Qualifier("authRedisTemplate")
    public RedisTemplate redisTemplate;

    private RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

//    public AuthRedisApi(RedisTemplate redisTemplate) {
//        super(redisTemplate);
//    }

    /**
     * 保存登录信息
     *
     * @param login
     * @return
     */
    public boolean saveLoginInfo(Login login) {
        return setBean(login.token.token, login, AuthConfig.DEFAULT_TOKEN_AGE);
    }

    /**
     * 获取登录信息
     *
     * @param token 用户唯一标识
     */
    public Login getLoginInfo(String token) {
        return getBean(token, Login.class);
    }

    /**
     * 清除登录信息
     *
     * @param token 用户唯一标识
     */
    public boolean clearLoginInfo(String token) {
        return del(token);
    }

    public boolean setBean(String key, Object obj, long expire) {
        String value = JsonUtil.toJson(obj);
        set(key, value);
        return expire(key, expire);
    }

    public <T> T getBean(String key, Class<T> clz) {
        String json = get(key);
        if (json != null) {
            return JsonUtil.toBean(json, clz);
        }
        return null;
    }

    private boolean set(final String key, final String value) {
        return (boolean) getRedisTemplate().execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer serializer = getRedisTemplate().getStringSerializer();
            connection.set(serializer.serialize(key), serializer.serialize(value));
            return true;
        });
    }

    private String get(final String key) {
        try {
            return (String) getRedisTemplate().execute((RedisCallback<String>) connection -> {
                RedisSerializer<String> serializer = getRedisTemplate().getStringSerializer();
                byte[] value = connection.get(serializer.serialize(key));
                return serializer.deserialize(value);
            });
        } catch (Exception e) {
            return null;
        }
    }

    private boolean del(String key) {
        return (boolean) getRedisTemplate().execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = getRedisTemplate().getStringSerializer();
            connection.del(serializer.serialize(key));
            return true;
        });
    }

    private boolean expire(final String key, long expire) {
        return getRedisTemplate().expire(key, expire, TimeUnit.SECONDS);
    }
}
