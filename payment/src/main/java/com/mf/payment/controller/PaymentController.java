package com.mf.payment.controller;

import com.mf.common.Payment;
import com.mf.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RefreshScope
public class PaymentController {

    @Autowired
    PaymentService paymentService;


    @GetMapping("/{id}")
    public ResponseEntity<Payment> payment(@PathVariable("id") Integer id) {

        return ResponseEntity.ok(paymentService.pay(id));
    }
}
