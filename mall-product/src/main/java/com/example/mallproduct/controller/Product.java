package com.example.mallproduct.controller;

import com.mf.mall.common.base.BaseResponse;
import com.mf.mall.common.dto.OrderItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class Product {
    @PutMapping("/products/stock")
    public BaseResponse checkAndDecreaseStock(@RequestBody List<OrderItemDTO> orderItemDTOList) {
        log.info("orderItemDTOList, {}", orderItemDTOList);
        return BaseResponse.success();
    }

}
