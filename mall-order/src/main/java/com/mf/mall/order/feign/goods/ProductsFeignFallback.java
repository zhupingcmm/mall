package com.mf.mall.order.feign.goods;

import com.mf.mall.common.base.BaseResponse;
import com.mf.mall.common.base.ResponseEnum;
import com.mf.mall.common.dto.OrderItemDTO;
import com.mf.mall.common.exception.BusinessException;

import java.util.List;


public class ProductsFeignFallback implements ProductsFeign {

    @Override
    public BaseResponse checkAndDecreaseStock(List<OrderItemDTO> orderItemDTOList) {
        throw new BusinessException(ResponseEnum.FEIGN_CALL_EXCEPTION);
    }
}
