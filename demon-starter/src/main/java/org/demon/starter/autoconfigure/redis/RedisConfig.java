package org.demon.starter.autoconfigure.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Environment environment;

    /**
     * 用户登录信息
     */
    private static final String AUTH_DB = "auth";

//    @Value("${spring.redis.host}")
//    private String host;
//
//    @Value("${spring.redis.port}")
//    private int port;
//
//    @Value("${spring.redis.timeout}")
//    private int timeout;
//
//    @Value("${spring.redis.pool.max-idle}")
//    private int maxIdle;
//
//    @Value("${spring.redis.pool.max-wait}")
//    private long maxWaitMillis;
//
//    @Value("${spring.redis.password}")
//    private String password;

    @Bean("authRedisTemplate")
    public RedisTemplate<String, Object> getRedisTemplate() {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(getConnectionFactory(AUTH_DB));
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    private JedisPoolConfig getRedisConfig(String db) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(Integer.parseInt(environment.getProperty("spring." + db + ".redis.pool.max-idle")));
        config.setMaxWaitMillis(Long.parseLong(environment.getProperty("spring." + db + ".redis.pool.max-wait")));
        return config;
    }

    private JedisConnectionFactory getConnectionFactory(String db) {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        JedisPoolConfig config = getRedisConfig(db);
        factory.setPoolConfig(config);
        factory.setHostName(environment.getProperty("spring." + db + ".redis.host}"));
        factory.setPassword(environment.getProperty("spring." + db + ".redis.password}"));
        factory.setTimeout(Integer.parseInt(environment.getProperty("spring." + db + ".redis.timeout")));
        logger.info("JedisConnectionFactory bean init success.");
        return factory;
    }
}
