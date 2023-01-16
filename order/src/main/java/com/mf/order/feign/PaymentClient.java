package com.mf.order.feign;

import com.mf.common.Message;
import com.mf.common.payment.PaymentVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "mall-payment", fallback = PaymentClient.Fallback.class)
@RequestMapping("payment")
public interface PaymentClient {

    @GetMapping("/{id}")
    Message payment(@PathVariable Integer id);

    @PostMapping
    PaymentVO addPayment(@RequestBody PaymentVO paymentVO);


    @Component
    @RequestMapping("fallback/payment")
    class Fallback implements PaymentClient {
        @Override
        public Message payment(Integer id) {
            Message payment = new Message(id, "熔断降级方法返回");
            return payment;
        }

        @Override
        public PaymentVO addPayment(PaymentVO paymentVO) {
            return new PaymentVO().setId(100L);
        }
    }
}
