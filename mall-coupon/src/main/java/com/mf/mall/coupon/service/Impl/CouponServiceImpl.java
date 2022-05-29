package com.mf.mall.coupon.service.Impl;

import com.mf.mall.common.dto.CouponDTO;
import com.mf.mall.common.util.Assert;
import com.mf.mall.common.util.ObjectTransformer;
import com.mf.mall.coupon.mapper.CouponMapper;
import com.mf.mall.coupon.model.CouponDO;
import com.mf.mall.coupon.service.ICouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements ICouponService {

    private final CouponMapper couponMapper;

    @Transactional
    @Override
    public boolean createCoupon(CouponDTO couponDTO) {
        CouponDO couponDO = ObjectTransformer.transform(couponDTO, CouponDO.class);
        int result = couponMapper.insertCoupon(couponDO);
        return Assert.singleRowAffected(result);
    }
}
