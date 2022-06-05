package com.mf.mall.product.aspect;

import com.google.common.util.concurrent.RateLimiter;
import com.mf.mall.common.base.ResponseEnum;
import com.mf.mall.common.exception.BusinessException;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CacheUtil {
    public static String getCacheKey(String key, String cacheNames, ProceedingJoinPoint joinPoint) {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(key);
        EvaluationContext context = new StandardEvaluationContext();

        // 动态设置上下文环境
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        String [] parameterNames = discoverer.getParameterNames(methodSignature.getMethod());
        Object [] args = joinPoint.getArgs();
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }
        return cacheNames + expression.getValue(context).toString();
    }

    public static void rateLimit(int waitInSeconds, ProceedingJoinPoint joinPoint, Map<String, RateLimiter> rateLimiterMap) {
        val signature = (MethodSignature) joinPoint.getSignature();
        RateLimiter rateLimiter = rateLimiterMap.get(signature.getMethod().getName());
        if (rateLimiter != null) {
//            int waitInSeconds = myCacheable.waitInSeconds();
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
}
