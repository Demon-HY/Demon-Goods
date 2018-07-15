package org.demon.starter.autoconfigure.redis;

import org.demon.utils.JsonUtil;
import org.demon.utils.beans.SerializeUtils;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RedisApi {

    private static final long serialVersionUID = -5502256331928465597L;

    private RedisTemplate redisTemplate;

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public boolean set(final String key, final String value) {
        return (boolean) getRedisTemplate().execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer serializer = getRedisTemplate().getStringSerializer();
            connection.set(serializer.serialize(key), serializer.serialize(value));
            return true;
        });
    }

    public boolean set(final String key, final String value, long expire) {
        set(key, value);
        return expire(key, expire);
    }

    public String get(final String key) {
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

    public Object getBean(final String key) {
        try {
            return getRedisTemplate().execute((RedisConnection connection) -> {
                RedisSerializer<String> serializer = getRedisTemplate().getStringSerializer();
                byte[] value = connection.get(serializer.serialize(key));
                return SerializeUtils.unserialize(value);
            });
        } catch (Exception e) {
            return null;
        }
    }

    public boolean expire(final String key, long expire) {
        return getRedisTemplate().expire(key, expire, TimeUnit.SECONDS);
    }

    public <T> boolean setList(String key, List<T> list) {
        String value = JsonUtil.toJson(list);
        return set(key, value);
    }

    public <T> List<T> getList(String key, Class<T> clz) {
        String json = get(key);
        if (json != null) {
            return JsonUtil.toList(json, clz);
        }
        return null;
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

    public long lpush(final String key, Object obj) {
        final String value = JsonUtil.toJson(obj);
        return (long) getRedisTemplate().execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = getRedisTemplate().getStringSerializer();
            return connection.lPush(serializer.serialize(key), serializer.serialize(value));
        });
    }

    public long rpush(final String key, Object obj) {
        final String value = JsonUtil.toJson(obj);
        return (long) getRedisTemplate().execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = getRedisTemplate().getStringSerializer();
            return (long) connection.rPush(serializer.serialize(key), serializer.serialize(value));
        });
    }

    public String lpop(final String key) {
        return (String) getRedisTemplate().execute((RedisCallback<String>) connection -> {
            RedisSerializer<String> serializer = getRedisTemplate().getStringSerializer();
            byte[] res = connection.lPop(serializer.serialize(key));
            return serializer.deserialize(res);
        });
    }

    public Long incr(String key, long growthLength) {
        return getRedisTemplate().opsForValue().increment(key, growthLength);
    }

    public boolean del(String key) {
        return (boolean) getRedisTemplate().execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = getRedisTemplate().getStringSerializer();
            connection.del(serializer.serialize(key));
            return true;
        });
    }
}
