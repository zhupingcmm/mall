package com.mf.payment.service;


import com.mf.common.Message;
import com.mf.payment.entity.Payment;

public interface PaymentService {

    Message pay(Integer id);

    Payment addPayment(Payment payment);
}
