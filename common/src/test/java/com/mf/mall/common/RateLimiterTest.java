package com.mf.mall.common;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class RateLimiterTest {

    @Test
    public void createTest(){
       RateLimiter rateLimiter = RateLimiter.create(1);
        System.out.println(rateLimiter.getRate());
        rateLimiter.setRate(2);
        System.out.println(rateLimiter.getRate());
    }


    @Test
    public void testAcquire(){
        RateLimiter rateLimiter = RateLimiter.create(1);
        while (true) {
            double timeSpent = rateLimiter.acquire(1);
            System.out.println(timeSpent);
        }
    }

    @Test
    public void testSmoothWarmingUp(){
        RateLimiter rateLimiter = RateLimiter.create(2, 4, TimeUnit.SECONDS);
        while (true) {
            System.out.println("get 1 token: " + rateLimiter.acquire(1) + "s");
        }
    }

    @Test
    public void testTryAcquire(){
        RateLimiter rateLimiter = RateLimiter.create(1);
        boolean result1 = rateLimiter.tryAcquire(10);
        System.out.println("获取10个令牌结束，结果：" + result1);

        boolean result2 = rateLimiter.tryAcquire(1, 2, TimeUnit.SECONDS);
        System.out.println("获取1个令牌结束, 结果:" + result2);
    }


}
