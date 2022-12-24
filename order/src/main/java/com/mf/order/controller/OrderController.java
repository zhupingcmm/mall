package com.mf.order.controller;

import com.mf.common.Payment;
import com.mf.order.feign.PaymentClient;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
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
    public ResponseEntity<Payment> getPaymentById(@PathVariable("id") Integer id) {
        val payment = paymentClient.payment(id);
        return ResponseEntity.ok(payment);
    }
}
