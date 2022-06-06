package com.mf.mall.coupon.controller;

import com.mf.mall.common.base.BaseResponse;
import com.mf.mall.common.dto.CouponRecordDTO;
import com.mf.mall.coupon.service.ICouponRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/couponRecord")
@RequiredArgsConstructor
public class CouponRecordController {
    private final ICouponRecordService couponRecordService;

    /**
     * 更新优惠券状态
     *
     * @param couponRecordDTO
     * @return
     */
    @PutMapping
    BaseResponse useCoupon(@RequestBody CouponRecordDTO couponRecordDTO){
        log.info("couponRecordDTO,{}", couponRecordDTO);
        couponRecordService.useCoupon(couponRecordDTO);
        return BaseResponse.success();
    }


    @PostMapping
    BaseResponse receiveCoupon(@RequestBody CouponRecordDTO couponRecordDTO){
        couponRecordService.receiveCoupon(couponRecordDTO);
        return BaseResponse.success();
    }

}
