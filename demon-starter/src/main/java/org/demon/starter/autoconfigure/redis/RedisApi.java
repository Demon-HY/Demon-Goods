package org.demon.starter.autoconfigure.redis;

import org.demon.utils.JsonUtil;
import org.demon.utils.beans.SerializeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RedisApi {
    
    private RedisTemplate redisTemplate;

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public RedisApi(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean set(final String key, final String value) {
        return (boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer serializer = redisTemplate.getStringSerializer();
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
            return (String) redisTemplate.execute((RedisCallback<String>) connection -> {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value = connection.get(serializer.serialize(key));
                return serializer.deserialize(value);
            });
        } catch (Exception e) {
            return null;
        }
    }

    public Object getBean(final String key) {
        try {
            return redisTemplate.execute((RedisConnection connection) -> {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value = connection.get(serializer.serialize(key));
                return SerializeUtils.unserialize(value);
            });
        } catch (Exception e) {
            return null;
        }
    }

    public boolean expire(final String key, long expire) {
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
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
        return (long) redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            return connection.lPush(serializer.serialize(key), serializer.serialize(value));
        });
    }

    public long rpush(final String key, Object obj) {
        final String value = JsonUtil.toJson(obj);
        return (long) redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            return (long) connection.rPush(serializer.serialize(key), serializer.serialize(value));
        });
    }

    public String lpop(final String key) {
        return (String) redisTemplate.execute((RedisCallback<String>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] res = connection.lPop(serializer.serialize(key));
            return serializer.deserialize(res);
        });
    }

    public Long incr(String key, long growthLength) {
        return redisTemplate.opsForValue().increment(key, growthLength);
    }

    public boolean del(String key) {
        return (boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            connection.del(serializer.serialize(key));
            return true;
        });
    }
}
