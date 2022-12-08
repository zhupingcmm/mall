package com.mf.payment.controller;

import com.mf.common.Payment;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Value("${server.port}")
    private String port;


    @GetMapping("/{id}")
    public ResponseEntity<Payment> payment(@PathVariable("id") Integer id) {
        val payment = Payment.builder().id(id).message("支付成功: " + port).build();
        return ResponseEntity.ok(payment);
    }
}
