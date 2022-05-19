package com.mf.mall.order.controller;

import com.mf.mall.common.base.BaseResponse;
import com.mf.mall.common.dto.OrderDTO;
import com.mf.mall.order.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

//    @Autowired
//    @Qualifier("IOrderServiceImpl")
    private final IOrderService iOrderService;

    @PostMapping("/order")
    public BaseResponse createOrder(@RequestBody OrderDTO orderDTO){
        iOrderService.createOrder(orderDTO);
        return BaseResponse.success();
    }

}
