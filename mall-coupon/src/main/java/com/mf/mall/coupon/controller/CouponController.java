package com.mf.mall.coupon.controller;

import com.mf.mall.common.base.BaseResponse;
import com.mf.mall.common.dto.CouponDTO;
import com.mf.mall.common.dto.CouponRecordDTO;
import com.mf.mall.coupon.service.ICouponRecordService;
import com.mf.mall.coupon.service.ICouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CouponController {

    private final ICouponService couponService;
    private final ICouponRecordService couponRecordService;

    /**
     * 更新优惠券状态
     *
     * @param couponRecordDTO
     * @return
     */
    @PutMapping("/couponRecord")
    BaseResponse useCoupon(@RequestBody CouponRecordDTO couponRecordDTO){
        log.info("couponRecordDTO,{}", couponRecordDTO);
        return BaseResponse.success();
    }

    @PostMapping("/coupon")
    BaseResponse createCoupon(@RequestBody CouponDTO couponDTO){
        couponService.createCoupon(couponDTO);
        return BaseResponse.success();
    }

    @PostMapping("/couponRecord")
    BaseResponse receiveCoupon(@RequestBody CouponRecordDTO couponRecordDTO){
        couponRecordService.receiveCoupon(couponRecordDTO);
        return BaseResponse.success();
    }
}
