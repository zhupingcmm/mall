package com.mf.mall.coupon.service;

import com.mf.mall.common.dto.CouponRecordDTO;

public interface ICouponRecordService {

    boolean receiveCoupon(CouponRecordDTO couponRecordDTO);

    boolean useCoupon(CouponRecordDTO couponRecordDTO);


}
