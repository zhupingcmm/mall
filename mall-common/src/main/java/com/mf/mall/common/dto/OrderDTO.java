package com.mf.mall.common.dto;

import com.mf.mall.common.base.BaseBean;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO extends BaseBean {
    /** 订单号 **/
    private Long orderNo;

    /** 订单金额 **/
    private BigDecimal amount;

    /** 订单状态 0-初始化 1-提交 2-完成 3-撤销 **/
    private Integer status;

    /** 用户ID **/
    private Long userId;

    /** 优惠券ID **/
    private Long couponRecordId;

    /** 订单明细列表 **/
    private List<OrderItemDTO> orderItemDTOList;
}
