package com.mf.mall.order.feign.coupon;

import com.mf.mall.common.base.BaseResponse;
import com.mf.mall.common.dto.CouponRecordDTO;
import com.mf.mall.order.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "mall-coupon",
        fallback = CouponFeignFallback.class,
        configuration = FeignConfig.class
)
public interface CouponFeign {
    @PostMapping("/couponRecord")
    BaseResponse receiveCoupon(@RequestBody CouponRecordDTO couponRecordDTO);

    @PutMapping("/couponRecord")
    BaseResponse useCoupon(@RequestBody CouponRecordDTO couponRecordDTO);
}
