package com.mf.payment.controller;

import com.mf.common.Payment;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${server.port}")
    private String port;

    @Value("${depart.name}")
    private String departName;


    @GetMapping("/{id}")
    public ResponseEntity<Payment> payment(@PathVariable("id") Integer id) {
        val payment = Payment.builder().id(id).message(String.format("支付成功: payment{port %s} {department %s}", port, departName) ).build();
        return ResponseEntity.ok(payment);
    }
}
