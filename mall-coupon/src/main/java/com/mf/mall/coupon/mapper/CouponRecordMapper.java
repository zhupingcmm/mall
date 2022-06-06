package com.mf.mall.coupon.mapper;

import com.mf.mall.coupon.model.CouponRecordDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CouponRecordMapper {
    /**
     * 新增优惠券记录，用于用户领取优惠券场景
     *
     * @param couponRecordDO
     * @return
     */
    int insertCouponRecord(CouponRecordDO couponRecordDO);

    /**
     * 根据用户Id 和优惠券Id查找优惠券
     * @param couponRecordDO
     * @return
     */
    CouponRecordDO selectCouponRecord(CouponRecordDO couponRecordDO);

    /**
     * 根据优惠券id 更新优惠券状态
     * @param couponRecordDO
     * @return
     */
    int updateCouponRecordStatus(CouponRecordDO couponRecordDO);
}
