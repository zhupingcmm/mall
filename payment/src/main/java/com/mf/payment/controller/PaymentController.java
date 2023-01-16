package com.mf.payment.controller;

import com.mf.common.Message;
import com.mf.common.payment.PaymentVO;
import com.mf.payment.convert.PaymentConvert;
import com.mf.payment.service.PaymentService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RefreshScope
public class PaymentController {

    @Autowired
    PaymentService paymentService;


    @GetMapping("/{id}")
    public ResponseEntity<Message> payment(@PathVariable("id") Integer id) {

        return ResponseEntity.ok(paymentService.pay(id));
    }

    @PostMapping
    public ResponseEntity<PaymentVO> addPayment(@RequestBody PaymentVO paymentVO) {

        val payment = paymentService.addPayment(PaymentConvert.INSTANCE.convertToEntity(paymentVO));
        return ResponseEntity.ok(PaymentConvert.INSTANCE.convertToVO(payment));
    }
}
