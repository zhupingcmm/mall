package com.mf.mall.product.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {
    private final ResourceLoader resourceLoader;

    @Value("#{${cacheable.expirekey}}")
    private Map<String,String> expirekey;

    @Bean
    public <V> RedisTemplate<String, V> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate <String, V> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 使用GenericJackson2JsonRedisSerializer 替换默认序列化
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        // 设置key和value的序列化规则
        redisTemplate.setKeySerializer(new GenericToStringSerializer<>(Object.class));
        redisTemplate.setKeySerializer(genericJackson2JsonRedisSerializer);
        // 设置hashkey和hashvalue的序列化规则
        redisTemplate.setHashKeySerializer(new GenericToStringSerializer<>(Object.class));
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//       return  RedisCacheManager.builder(redisConnectionFactory)
//                .cacheDefaults(getDefaultCacheConfiguration())
//                .withInitialCacheConfigurations(getCacheConfigurations())
//                .transactionAware()
//                .build();
//    }
//
//    private Map<String, RedisCacheConfiguration> getCacheConfigurations() {
//        Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>();
//        //缓存键,配置默认过期时间
//        if (this.expirekey != null) {
//            for (String s : this.expirekey.keySet()) {
//                configurationMap.put(s, this.getDefaultCacheConfiguration(Long.valueOf(this.expirekey.get(s))));
//            }
//        }
//        return configurationMap;
//    }
//
//    private RedisCacheConfiguration getDefaultCacheConfiguration(long seconds) {
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
//        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(
//                RedisSerializationContext
//                        .SerializationPair
//                        .fromSerializer(jackson2JsonRedisSerializer)
//        ).entryTtl(Duration.ofSeconds(seconds));
//        return redisCacheConfiguration;
//    }
//
//
//    private RedisCacheConfiguration getDefaultCacheConfiguration(){
//        return RedisCacheConfiguration.defaultCacheConfig(resourceLoader.getClassLoader());
//    }

}
