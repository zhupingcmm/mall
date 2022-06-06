package com.mf.mall.coupon.controller;

import com.mf.mall.common.base.BaseResponse;
import com.mf.mall.common.dto.CouponDTO;
import com.mf.mall.coupon.service.ICouponRecordService;
import com.mf.mall.coupon.service.ICouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CouponController {

    private final ICouponService couponService;

    @PostMapping("/coupon")
    BaseResponse createCoupon(@RequestBody CouponDTO couponDTO){
        couponService.createCoupon(couponDTO);
        return BaseResponse.success();
    }

}
