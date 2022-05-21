package com.mf.mall.coupon.controller;

import com.mf.mall.common.base.BaseResponse;
import com.mf.mall.common.dto.CouponRecordDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CouponController {

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
}
