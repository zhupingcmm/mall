package com.mf.mall.product.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class TwoAspect {

    @Pointcut("execution( * com.mf.mall.product.service.impl.HelloServiceImpl.sayHi(..)))")
    public void pointCut(){}

    @Before("pointCut()")
    public void before(){
        System.out.println("TwoAspect before!!!");
    }

    @Around("pointCut()")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("TwoAspect around");
        return joinPoint.proceed();
    }

    @After("pointCut()")
    public void after(){
        System.out.println("TwoAspect after!!!");
    }
}
