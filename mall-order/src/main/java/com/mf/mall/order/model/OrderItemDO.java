package com.mf.mall.order.model;

import com.mf.mall.common.base.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderItemDO extends BaseBean {
    private Long id;

    private Long orderNo;

    private Long goodsId;

    private Long number;

    private BigDecimal amount;

    private Date createTime;

    private Date updateTime;
}
