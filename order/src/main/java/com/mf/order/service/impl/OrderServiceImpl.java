package com.mf.order.service.impl;

import com.mf.common.payment.PaymentVO;
import com.mf.order.entity.Order;
import com.mf.order.feign.PaymentClient;
import com.mf.order.repository.OrderRepository;
import com.mf.order.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Qualifier("com.mf.order.feign.PaymentClient")
    @Autowired
    private PaymentClient paymentClient;


    @Override
    @GlobalTransactional
    public Order addOrder(Long id, Order order) {
        val paymentVO = new PaymentVO()
                .setProductName(order.getProductName())
                .setUnitPrice(order.getUnitPrice())
                .setCount(order.getCount())
                .setAmount(order.getUnitPrice() * order.getCount());
        paymentClient.addPayment(paymentVO);

        // 手动异常
        int i = (int) (3 / id);

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).get();
    }
}
