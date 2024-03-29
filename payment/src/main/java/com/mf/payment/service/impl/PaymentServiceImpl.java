package com.mf.payment.service.impl;

import com.mf.common.Message;
import com.mf.payment.entity.Payment;
import com.mf.payment.repository.PaymentRepository;
import com.mf.payment.service.PaymentService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
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
    @Transactional
    public Payment addPayment(Payment payment) {
        val xid = RootContext.getXID();
        log.info("xid:::::: ,{}", xid);
        return paymentRepository.save(payment);
    }
}
