package com.mf.mall.product.aspect;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyCacheEvict {

    String cacheNames();

    String key();

    boolean beforeInvocation() default false;

    boolean allEntries() default false;
}
