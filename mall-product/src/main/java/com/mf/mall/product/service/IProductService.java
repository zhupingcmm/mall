package com.mf.mall.product.service;

import com.mf.mall.common.dto.ProductsDTO;

public interface IProductService {
    ProductsDTO getProduct(Long id);
}
