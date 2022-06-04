package com.mf.mall.order.feign.coupon;

import com.mf.mall.common.base.BaseResponse;
import com.mf.mall.common.base.ResponseEnum;

import com.mf.mall.common.dto.CouponRecordDTO;
import com.mf.mall.common.exception.BusinessException;

public class CouponFeignFallback implements CouponFeign{
    @Override
    public BaseResponse receiveCoupon(CouponRecordDTO couponRecordDTO) {
        throw new BusinessException(ResponseEnum.FEIGN_CALL_EXCEPTION);
    }

    @Override
    public BaseResponse useCoupon(CouponRecordDTO couponRecordDTO) {
        throw new BusinessException(ResponseEnum.FEIGN_CALL_EXCEPTION);
    }
}
