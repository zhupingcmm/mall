package com.mf.mall.product.aspect;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyCachePut {
    String cacheNames();

    String key();
}
