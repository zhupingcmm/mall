package com.mf.mall.order.feign.goods;

import com.mf.mall.common.base.BaseResponse;
import com.mf.mall.common.dto.OrderItemDTO;
import com.mf.mall.order.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@FeignClient(
        name = "mall-product",
        fallback = ProductsFeignFallback.class,
        configuration = FeignConfig.class
)
public interface ProductsFeign {
    @PutMapping("/products/stock")
    BaseResponse checkAndDecreaseStock(@RequestBody List<OrderItemDTO> orderItemDTOList);
}
