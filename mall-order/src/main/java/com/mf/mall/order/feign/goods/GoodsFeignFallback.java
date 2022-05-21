package com.mf.mall.order.feign.goods;

import com.mf.mall.common.base.BaseResponse;
import com.mf.mall.common.base.ResponseEnum;
import com.mf.mall.common.exception.BusinessException;


public class GoodsFeignFallback implements GoodsFeign{

    @Override
    public BaseResponse checkAndDecreaseStock() {
        throw new BusinessException(ResponseEnum.FEIGN_CALL_EXCEPTION);
    }
}
