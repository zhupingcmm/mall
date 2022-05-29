package com.mf.mall.coupon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponDO {
    private Long id;

    private String title;

    private BigDecimal withAmount;

    private BigDecimal usedAmount;

    private Long quota;

    private Long takeCount;

    private Long usedCount;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
