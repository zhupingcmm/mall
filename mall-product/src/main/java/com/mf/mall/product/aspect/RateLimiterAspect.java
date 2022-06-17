package com.mf.mall.product.aspect;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.mf.mall.common.annotation.MyRateLimiter;
import com.mf.mall.common.base.BaseResponse;
import com.mf.mall.common.base.ResponseEnum;
import com.mf.mall.common.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class RateLimiterAspect {

    private Map<String, RateLimiter> limiterMap = Maps.newConcurrentMap();

    @Around("@annotation(myRateLimiter)")
    public Object process(ProceedingJoinPoint joinPoint, MyRateLimiter myRateLimiter) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String resource = request.getRequestURI();

        log.debug("{} 开始进行限流处理...", resource);
        RateLimiter rateLimiter = this.getRateLimiter(resource, myRateLimiter);

        boolean acquired = rateLimiter.tryAcquire(myRateLimiter.permits(), myRateLimiter.timeout(), TimeUnit.SECONDS);

        if (!acquired) {
            log.warn("{} 触发了限流!!!", resource);
            this.outputResponse();
        }

        return joinPoint.proceed();
    }

    private RateLimiter getRateLimiter(String resource, MyRateLimiter myRateLimiter) {
       RateLimiter rateLimiter = limiterMap.get(resource);
       if (rateLimiter == null) {
           rateLimiter = createRateLimiter(myRateLimiter);
           limiterMap.put(resource, rateLimiter);
       }
       return rateLimiter;
    }

    private RateLimiter createRateLimiter(MyRateLimiter myRateLimiter) {
        RateLimiter rateLimiter;
        if (myRateLimiter.isWarmup()) {
            rateLimiter = RateLimiter.create(myRateLimiter.permitPerSecond(), myRateLimiter.warmupPeriod(), myRateLimiter.warmupTimeUnit());
        } else {
            rateLimiter = RateLimiter.create(myRateLimiter.permitPerSecond());
        }
        return rateLimiter;
    }

    private void outputResponse () {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        try(ServletOutputStream outputStream = response.getOutputStream()){
            BaseResponse baseResponse = new BaseResponse(ResponseEnum.SYSTEM_ERROR);
            outputStream.write(JSONUtil.toJSONString(baseResponse).getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
