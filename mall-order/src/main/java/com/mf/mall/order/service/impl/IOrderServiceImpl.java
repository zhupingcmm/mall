package com.mf.mall.order.service.impl;

import com.mf.mall.common.dto.OrderDTO;
import com.mf.mall.common.util.ObjectTransformer;
import com.mf.mall.order.mapper.OrderMapper;
import com.mf.mall.order.model.OrderDO;
import com.mf.mall.order.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class IOrderServiceImpl implements IOrderService {

    private final OrderMapper orderMapper;

    @Override
    public boolean createOrder(OrderDTO orderDTO) {

        // 保存订单到数据库
        OrderDO orderDO = ObjectTransformer.transform(orderDTO, OrderDO.class);
        int result = orderMapper.insertOrder(orderDO);
        log.info("Success save order into db {}", result);
        return true;
    }
}
