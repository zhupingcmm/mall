package com.mf.mall.order.model;

import com.mf.mall.common.base.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderDO extends BaseBean {

    private Long id;

    private Long orderNo;

    private BigDecimal amount;

    private Integer status;

    private Long userId;

    private Long couponRecordId;

    private Date createTime;

    private Date updateTime;

}
