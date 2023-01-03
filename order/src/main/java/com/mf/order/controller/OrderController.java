package com.mf.order.controller;

import com.mf.common.Payment;
import com.mf.order.feign.PaymentClient;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Qualifier("com.mf.order.feign.PaymentClient")
    @Autowired
    private PaymentClient paymentClient;


    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("load-balance")
    public String getLoadBalance(){
        val instance = loadBalancerClient.choose("mall-payment");
        return instance.getHost() + " : " + instance.getPort();
    }

    @GetMapping("/payment/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable("id") Integer id, String color) {
        log.info("color is {}", color );
        val payment = paymentClient.payment(id);
        return ResponseEntity.ok(payment);
    }
}
