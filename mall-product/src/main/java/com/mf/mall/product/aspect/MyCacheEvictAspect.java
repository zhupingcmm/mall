package com.mf.mall.product.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class MyCacheEvictAspect {
    private final RedisTemplate<String, Object> redisTemplate;

    @Around("@annotation(myCacheEvict)")
    public void doAroundCache(ProceedingJoinPoint joinPoint, MyCacheEvict myCacheEvict) throws Throwable {
        // 获取缓存key
        String cacheKey = CacheUtil.getCacheKey(myCacheEvict.key(), myCacheEvict.cacheNames(), joinPoint);

        if (!myCacheEvict.beforeInvocation()) {
            // 从数据库删除数据
            joinPoint.proceed();
            // 从缓存中删除数据
            if (!myCacheEvict.allEntries()) {
                redisTemplate.delete(cacheKey);
            }
            val keys = redisTemplate.keys("*");
            assert keys != null;
            redisTemplate.delete(keys);
        } else {
            // 从缓存中删除数据
            if (!myCacheEvict.allEntries()) {
                redisTemplate.delete(cacheKey);
            }
            val keys = redisTemplate.keys("*");
            assert keys != null;
            redisTemplate.delete(keys);

            // 从数据库删除数据
            joinPoint.proceed();
        }

    }


}
