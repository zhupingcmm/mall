package com.mf.mall.order.config;

import com.mf.mall.order.feign.coupon.CouponFeignFallback;
import com.mf.mall.order.feign.goods.GoodsFeignFallback;
import org.springframework.context.annotation.Bean;

public class FeignConfig {
    @Bean
    public CouponFeignFallback couponFeignFallback() {
        return  new CouponFeignFallback();
    }

    @Bean
    public GoodsFeignFallback goodsFeignFallback() {
        return new GoodsFeignFallback();
    }
}
