package com.mf.mall.coupon.service;

import com.mf.mall.common.dto.CouponDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
public class CouponServiceTest {
    @Autowired
    private ICouponService couponService;

    @Test
    public void testCreateCoupon() {
        CouponDTO couponDTO= CouponDTO.builder()
                .title("test")
                .withAmount(BigDecimal.valueOf(100))
                .usedAmount(BigDecimal.valueOf(10))
                .quota(Long.valueOf(10000))
                .takeCount(0l)
                .usedCount(0l)
                .status(1)
                .build();
       boolean result = couponService.createCoupon(couponDTO);
        System.out.println(result);
    }

}
