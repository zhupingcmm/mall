package com.mf.mall.order.feign.goods;

import com.mf.mall.common.base.BaseResponse;
import com.mf.mall.order.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(
        name = "mall-goods",
        fallback = GoodsFeignFallback.class,
        configuration = FeignConfig.class
)
public interface GoodsFeign {
    @GetMapping("/goods/stock")
    BaseResponse checkAndDecreaseStock();
}
