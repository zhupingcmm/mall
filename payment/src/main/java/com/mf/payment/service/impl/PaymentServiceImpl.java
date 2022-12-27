package com.mf.payment.service.impl;

import com.mf.common.Payment;
import com.mf.payment.service.PaymentService;
import com.mf.plock.springboot.starter.annotation.Plock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Value("${server.port}")
    private String port;

    @Value("${depart.name}")
    private String departName;

    @Plock
    @Override
    public Payment pay(Integer id) {
        return Payment.builder().id(id).message(String.format("支付成功: payment{port %s} {department %s}", port, departName) ).build();
    }
}
