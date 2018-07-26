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

    public static boolean set(RedisTemplate redisTemplate, final String key, final String value) {
        return (boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer serializer = redisTemplate.getStringSerializer();
            connection.set(serializer.serialize(key), serializer.serialize(value));
            return true;
        });
    }

    public static boolean set(RedisTemplate redisTemplate, final String key, final String value, long expire) {
        set(redisTemplate, key, value);
        return expire(redisTemplate, key, expire);
    }

    public static String get(RedisTemplate redisTemplate, final String key) {
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

    public static Object getBean(RedisTemplate redisTemplate, final String key) {
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

    public static boolean expire(RedisTemplate redisTemplate, final String key, long expire) {
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    public static <T> boolean setList(RedisTemplate redisTemplate, String key, List<T> list) {
        String value = JsonUtil.toJson(list);
        return set(redisTemplate, key, value);
    }

    public static <T> List<T> getList(RedisTemplate redisTemplate, String key, Class<T> clz) {
        String json = get(redisTemplate, key);
        if (json != null) {
            return JsonUtil.toList(json, clz);
        }
        return null;
    }

    public static boolean setBean(RedisTemplate redisTemplate, String key, Object obj, long expire) {
        String value = JsonUtil.toJson(obj);
        set(redisTemplate, key, value);
        return expire(redisTemplate, key, expire);
    }

    public static <T> T getBean(RedisTemplate redisTemplate, String key, Class<T> clz) {
        String json = get(redisTemplate, key);
        if (json != null) {
            return JsonUtil.toBean(json, clz);
        }
        return null;
    }

    public static long lpush(RedisTemplate redisTemplate, final String key, Object obj) {
        final String value = JsonUtil.toJson(obj);
        return (long) redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            return connection.lPush(serializer.serialize(key), serializer.serialize(value));
        });
    }

    public static long rpush(RedisTemplate redisTemplate, final String key, Object obj) {
        final String value = JsonUtil.toJson(obj);
        return (long) redisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            return (long) connection.rPush(serializer.serialize(key), serializer.serialize(value));
        });
    }

    public static String lpop(RedisTemplate redisTemplate, final String key) {
        return (String) redisTemplate.execute((RedisCallback<String>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] res = connection.lPop(serializer.serialize(key));
            return serializer.deserialize(res);
        });
    }

    public static Long incr(RedisTemplate redisTemplate, String key, long growthLength) {
        return redisTemplate.opsForValue().increment(key, growthLength);
    }

    public static boolean del(RedisTemplate redisTemplate, String key) {
        return (boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            connection.del(serializer.serialize(key));
            return true;
        });
    }
}
