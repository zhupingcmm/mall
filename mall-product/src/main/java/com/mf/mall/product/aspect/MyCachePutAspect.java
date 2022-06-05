package com.mf.mall.product.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class MyCachePutAspect {
    private final RedisTemplate<String, Object> redisTemplate;

    @Around("@annotation(myCachePut)")
    public void doAroundCache(ProceedingJoinPoint joinPoint, MyCachePut myCachePut) throws Throwable {
        // 获取cache key
        String cacheKey = CacheUtil.getCacheKey(myCachePut.key(), myCachePut.cacheNames(), joinPoint);
        // 执行 业务逻辑本身， 获取数据库数据
        Object result = joinPoint.proceed();
        // 更新缓存数据
        redisTemplate.opsForValue().set(cacheKey, result);

    }
}
