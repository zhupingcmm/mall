package com.mf.mall.common.dto;

import com.mf.mall.common.base.BaseBean;
import lombok.*;



@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CouponRecordDTO extends BaseBean{
    /**
     * 优惠券记录ID
     **/
    private Long id;

    /**
     * 用户ID
     **/
    private Long userId;

    /**
     * 优惠券ID
     **/
    private Long couponId;

    /**
     * 状态 0-已领取未使用 1-已使用 -1为已过期
     **/
    private Integer status;
}
