package com.mf.mall.order.service;

import com.mf.mall.common.dto.OrderDTO;

public interface IOrderService {

    boolean createOrder(OrderDTO orderDTO);
}
