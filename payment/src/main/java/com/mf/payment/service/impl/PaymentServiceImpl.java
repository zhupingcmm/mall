package com.mf.payment.service.impl;

import com.mf.common.Message;
import com.mf.payment.entity.Payment;
import com.mf.payment.repository.PaymentRepository;
import com.mf.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Value("${server.port}")
    private String port;

    @Value("${depart.name}")
    private String departName;

    @Autowired
    private PaymentRepository paymentRepository;

//    @Plock
    @Override
    public Message pay(Integer id) {
//        return new Payment().setId(Long.valueOf(port)).setAmount(1.0).setUnitPrice(10.0);
        return Message.builder().id(id).message(String.format("支付成功: payment{port %s} {department %s}", port, departName) ).build();
    }

    @Override
    public Payment addPayment(Payment payment) {
        return paymentRepository.save(payment);
    }
}
