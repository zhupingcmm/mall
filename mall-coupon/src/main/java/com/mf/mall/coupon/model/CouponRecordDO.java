package com.mf.mall.coupon.model;

import com.mf.mall.common.base.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponRecordDO extends BaseBean {
    private Long id;

    private Long userId;

    private Long couponId;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
