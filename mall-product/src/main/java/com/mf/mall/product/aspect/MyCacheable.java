package com.mf.mall.product.aspect;

import java.lang.annotation.*;

/**
 * 自定义缓存注解
 * 用于在方法执行之前判断缓存是否存在
 * 存在则直接返回
 * 不存在则查数据库，再设置缓存
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyCacheable {
    /**
     * 缓存的名称前缀，完整的缓存名称生成规则: {cacheName}:{key}
     * @return
     */
    String cacheNames();

    /**
     *
     * @return
     */
    String key();

    /**
     * 缓存的过期时间，单位为妙，默认不设置过期时间
     * @return
     */

    int expireInSeconds() default 0;

    /**
     *限流器获取令牌等待超时时间
     */
    int waitInSeconds() default 0;
}
