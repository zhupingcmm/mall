package com.mf.order.controller;

import com.mf.common.Payment;
import com.mf.order.controller.vo.OrderVO;
import com.mf.order.convert.OrderConvert;
import com.mf.order.feign.PaymentClient;
import com.mf.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Qualifier("com.mf.order.feign.PaymentClient")
    @Autowired
    private PaymentClient paymentClient;

    @Autowired
    private OrderService orderService;


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


    @PostMapping
    public ResponseEntity<OrderVO> addOrder(@RequestBody OrderVO orderVO){
        val order = orderService.addOrder(OrderConvert.INSTANCE.convertToEntity(orderVO));
        return ResponseEntity.ok(OrderConvert.INSTANCE.convertToVo(order));
    }
}
