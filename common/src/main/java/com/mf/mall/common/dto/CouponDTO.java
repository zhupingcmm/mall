package com.mf.mall.common.dto;

import com.mf.mall.common.base.BaseBean;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class CouponDTO extends BaseBean {
    /** 优惠券ID **/
    private Long id;

    /** 优惠券标题 **/
    private String title;

    /** 满减金额 **/
    private BigDecimal withAmount;

    /** 优惠金额 **/
    private BigDecimal usedAmount;

    /** 发放数量 **/
    private Long quota;

    /** 已领取数量 **/
    private Long takeCount;

    /** 已使用数量 **/
    private Long usedCount;

    /** 状态 1-生效 2-失效 **/
    private Integer status;

}