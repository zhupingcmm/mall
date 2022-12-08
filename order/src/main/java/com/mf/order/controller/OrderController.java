package com.mf.order.controller;

import com.mf.common.Payment;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order")
public class OrderController {

    private static final String paymentService = "http://mall-payment";
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/payment/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable("id") Integer id) {
        val url = String.format("%s/payment/%d", paymentService, id);
        val payment = restTemplate.getForObject(url, Payment.class);
        return ResponseEntity.ok(payment);

    }
}
