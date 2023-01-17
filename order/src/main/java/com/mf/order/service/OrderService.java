package com.mf.order.service;

import com.mf.order.entity.Order;

import java.util.List;

public interface OrderService {
    Order addOrder(Long id, Order order);
    List<Order> getOrders();
    Order getOrderById(Long id);
}
