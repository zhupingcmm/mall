package com.mf.mall.product.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class WebLogAspect {

    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.mf.mall.product.controller.*.*(..))")
    public void webLogPointcut() {}

    @Before("webLogPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //获取请求头中的User-Agent
        startTime.set(System.currentTimeMillis());
        log.debug("request start time: {}", LocalDateTime.now());
        log.debug("request url is: {}, and request method is: {}, request ip is: {}", request.getRequestURL(), request.getMethod(), request.getRemoteAddr());
        log.debug("请求方法 : {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.debug("请求参数 : {}", Arrays.toString(joinPoint.getArgs()));

    }

    @AfterReturning(returning = "ret", pointcut = "webLogPointcut()")
    public void doAfterReturning(Object ret){
        log.debug("请求结束时间：{}", LocalDateTime.now());
        log.info("请求耗时：{}", (System.currentTimeMillis() - startTime.get()));
        log.debug("请求返回 : {}", ret);
    }
}
