package com.mf.mall.order.config;

import com.mf.mall.order.feign.coupon.CouponFeignFallback;
import com.mf.mall.order.feign.goods.ProductsFeignFallback;
import org.springframework.context.annotation.Bean;

public class FeignConfig {
    @Bean
    public CouponFeignFallback couponFeignFallback() {
        return  new CouponFeignFallback();
    }

    @Bean
    public ProductsFeignFallback productsFeignFallback() {
        return new ProductsFeignFallback();
    }
}
