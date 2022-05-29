package com.mf.mall.coupon.mapper;

import com.mf.mall.coupon.model.CouponDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CouponMapper {
    /**
     * 新增优惠券
     * @param couponDO
     * @return
     */
    int insertCoupon(CouponDO couponDO);
}
