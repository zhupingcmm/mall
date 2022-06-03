package com.mf.mall.coupon.service.Impl;

import com.mf.mall.common.base.ResponseEnum;
import com.mf.mall.common.dto.CouponRecordDTO;
import com.mf.mall.common.exception.BusinessException;
import com.mf.mall.common.util.Assert;
import com.mf.mall.common.util.ObjectTransformer;
import com.mf.mall.coupon.mapper.CouponMapper;
import com.mf.mall.coupon.mapper.CouponRecordMapper;
import com.mf.mall.coupon.model.CouponDO;
import com.mf.mall.coupon.model.CouponRecordDO;
import com.mf.mall.coupon.service.ICouponRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponRecordServiceImpl implements ICouponRecordService {

    private final CouponMapper couponMapper;
    private final CouponRecordMapper couponRecordMapper;

    ReentrantLock lock = new ReentrantLock();


    @Override
    @Transactional
    public boolean receiveCoupon(CouponRecordDTO couponRecordDTO) {
        try {
            lock.lock();
            long couponId = couponRecordDTO.getCouponId();

            CouponDO couponDO = couponMapper.selectCouponById(couponId);
            log.info("Get userId {}, id is {}", couponRecordDTO.getUserId(), couponDO.getId());
            Assert.notNull(couponDO);

            /** 新增优惠券领取记录*/
            long newTakeCount = couponDO.getTakeCount() + 1;
            if (newTakeCount > couponDO.getQuota()) {
                throw new BusinessException(ResponseEnum.COUPON_NOT_ENOUGH);
            }

            /** 新增优惠券领取记录*/
            couponRecordDTO.setStatus(0);
            CouponRecordDO couponRecordDO = ObjectTransformer.transform(couponRecordDTO, CouponRecordDO.class);
            int result = couponRecordMapper.insertCouponRecord(couponRecordDO);
            Assert.singleRowAffected(result);

            /**更新优惠券已领取数量*/
            couponDO.setTakeCount(newTakeCount);
            result = couponMapper.updateCouponTakeCount(couponDO);
            return Assert.singleRowAffected(result);
        } finally {
            lock.unlock();
        }
    }
}
