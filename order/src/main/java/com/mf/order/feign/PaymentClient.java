package com.mf.order.feign;

import com.mf.common.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "mall-payment", fallback = PaymentClient.Fallback.class)
@RequestMapping("payment")
public interface PaymentClient {

    @GetMapping("/{id}")
    Payment payment(@PathVariable Integer id);

    @Component
    @RequestMapping("fallback/payment")
    class Fallback implements PaymentClient {
        @Override
        public Payment payment(Integer id) {
            Payment payment = new Payment(id, "熔断降级方法返回");
            return payment;
        }
    }
}
