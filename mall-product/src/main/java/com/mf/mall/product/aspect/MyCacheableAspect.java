package com.mf.mall.product.aspect;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.mf.mall.common.base.ResponseEnum;
import com.mf.mall.common.exception.BusinessException;
import com.mf.mall.product.config.MyCacheableConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
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
        String cacheKey = getCacheKey(joinPoint, myCacheable);

        Object cacheValue = redisTemplate.opsForValue().get(cacheKey);
        if (cacheValue != null) {
            log.info("key:{}, cacheValue:{}", cacheKey, cacheValue);
            return cacheValue;
        }

        // 限流处理
        rateLimit(joinPoint, myCacheable);
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

    private void rateLimit(ProceedingJoinPoint joinPoint, MyCacheable myCacheable) {
        val signature = (MethodSignature) joinPoint.getSignature();
        RateLimiter rateLimiter = rateLimiterMap.get(signature.getMethod().getName());
        if (rateLimiter != null) {
            int waitInSeconds = myCacheable.waitInSeconds();
            if (waitInSeconds <= 0) {
                rateLimiter.acquire();
            } else {
              boolean acquire = rateLimiter.tryAcquire(waitInSeconds, TimeUnit.SECONDS);
              if (!acquire) {
                  throw new BusinessException(ResponseEnum.SYSTEM_ERROR);
              }
            }

        }
    }

    private String getCacheKey(ProceedingJoinPoint joinPoint, MyCacheable myCacheable) {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(myCacheable.key());
        EvaluationContext context = new StandardEvaluationContext();

        // 动态设置上下文环境
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        String [] parameterNames = discoverer.getParameterNames(methodSignature.getMethod());
        Object [] args = joinPoint.getArgs();
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }
        return myCacheable.cacheNames() + expression.getValue(context).toString();
    }
}
