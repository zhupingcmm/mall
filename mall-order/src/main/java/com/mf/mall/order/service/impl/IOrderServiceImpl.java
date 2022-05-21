package com.mf.mall.order.service.impl;

import com.mf.mall.common.base.BaseResponse;
import com.mf.mall.common.dto.CouponRecordDTO;
import com.mf.mall.common.dto.OrderDTO;
import com.mf.mall.common.dto.OrderItemDTO;
import com.mf.mall.common.util.ObjectTransformer;
import com.mf.mall.order.feign.coupon.CouponFeign;
import com.mf.mall.order.feign.goods.GoodsFeign;
import com.mf.mall.order.mapper.OrderItemMapper;
import com.mf.mall.order.mapper.OrderMapper;
import com.mf.mall.order.model.OrderDO;
import com.mf.mall.order.model.OrderItemDO;
import com.mf.mall.order.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class IOrderServiceImpl implements IOrderService {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final CouponFeign couponFeign;
    private final GoodsFeign goodsFeign;

    @Override
    public boolean createOrder(OrderDTO orderDTO) {
        /** 1、优惠券处理 **/
        if (orderDTO.getCouponRecordId() != null) {
             CouponRecordDTO couponRecordDTO = CouponRecordDTO.builder()
                    .id(orderDTO.getCouponRecordId())
                    .userId(orderDTO.getUserId())
                    .build();
             BaseResponse useCouponResponse = couponFeign.useCoupon(couponRecordDTO);
             log.info("useCouponResponse::{}", useCouponResponse);
        }
        /** 2.检查并且扣减库存 **/
        List<OrderItemDTO> orderItemDTOList = orderDTO.getOrderItemDTOList();
        BaseResponse stockResponse = goodsFeign.checkAndDecreaseStock();
        log.info("goodsFeign::, {}", stockResponse);


        // 3.保存订单到数据库
//        OrderDO orderDO = ObjectTransformer.transform(orderDTO, OrderDO.class);
//        int result = orderMapper.insertOrder(orderDO);
//        log.info("Success save order into db order {}", result);

        /** 4.保存订单详细 **/
//        List<OrderItemDO> orderItemDOList = ObjectTransformer.transform(orderItemDTOList, OrderItemDO.class);
//        orderItemMapper.insertOrderItems(orderItemDOList);
//        log.info("Success save order into db orderItem {}", result);

        return true;
    }
}
