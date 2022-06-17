package com.mf.mall.product.aspect;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.mf.mall.product.config.MyCacheableConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
@Aspect
public class MyCacheableAspect {
    private final RedisTemplate<String, Object> redisTemplate;
    private final MyCacheableConfig myCacheableConfig;

//    @Pointcut("@annotation(com.mf.mall.product.aspect.MyCacheable)")
//    public void pointCut(){
//    }

    private final Map<String, RateLimiter> rateLimiterMap= Maps.newHashMap();

    @PostConstruct
    private void initRateLimiterMap () {
        Map<String, Double> map = myCacheableConfig.getRateLimit().getMap();
        if (!CollectionUtils.isEmpty(map)) {
            map.forEach((methodName, permits) -> {
                RateLimiter rateLimiter = RateLimiter.create(permits);
                rateLimiterMap.put(methodName, rateLimiter);
            });
        }
    }

    @Around("@annotation(myCacheable)")
    public Object doAroundCache(ProceedingJoinPoint joinPoint, MyCacheable myCacheable) throws Throwable {

        //获取缓存key
        String cacheKey = CacheUtil.getCacheKey(myCacheable.key(), myCacheable.cacheNames(), joinPoint);

        Object cacheValue = redisTemplate.opsForValue().get(cacheKey);
        if (cacheValue != null) {
            log.info("key:{}, cacheValue:{}", cacheKey, cacheValue);
            return cacheValue;
        }

        // 限流处理
//        CacheUtil.rateLimit(myCacheable.waitInSeconds(), joinPoint, rateLimiterMap);
        // 查询数据库
        cacheValue = joinPoint.proceed();
        // 过期时间的处理
        if (myCacheable.expireInSeconds() <= 0) {
            redisTemplate.opsForValue().set(cacheKey, cacheValue);
        } else {
            redisTemplate.opsForValue().set(cacheKey, cacheValue, myCacheable.expireInSeconds(), TimeUnit.SECONDS);
        }

        return cacheValue;
    }

}
