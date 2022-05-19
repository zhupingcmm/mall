package com.mf.mall.common.dto;

import com.mf.mall.common.base.BaseBean;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO extends BaseBean {
    /** 订单明细ID **/
    private Long id;

    /** 订单号 **/
    private Long orderNo;

    /** 商品ID **/
    private Long goodsId;

    /** 数量 **/
    private Long number;

    /** 金额 **/
    private BigDecimal amount;
}
