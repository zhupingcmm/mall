package com.mf.mall.coupon.service;

import com.mf.mall.common.dto.CouponRecordDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CouponRecordServiceTest {
    @Autowired
    private ICouponRecordService couponRecordService;

    private static final int concurrency = 100;
    private CountDownLatch countDownLatch = new CountDownLatch(concurrency);

    @Test
    public void testCouponRecordService(){
//        CouponRecordDTO couponRecordDTO = initCouponRecordDTO(1l);
//        couponRecordService.receiveCoupon(couponRecordDTO);
        for (int i = 0; i < concurrency; i++) {
            long userId = i;
            new Thread(() -> {
                countDownLatch.countDown();
                try {
                    countDownLatch.await();

                    CouponRecordDTO couponRecordDTO = initCouponRecordDTO(userId);
//                    System.out.println("userId" + couponRecordDTO.getUserId());
                    couponRecordService.receiveCoupon(couponRecordDTO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private CouponRecordDTO initCouponRecordDTO(Long userId){
        return CouponRecordDTO.builder()
                .userId(userId)
                .couponId(1l)
                .status(0)
                .build();
    }
}
