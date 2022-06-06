package com.mf.mall.product.service;

import com.mf.mall.common.dto.OrderItemDTO;
import com.mf.mall.common.dto.ProductsDTO;

import java.util.List;

public interface IProductService {
    ProductsDTO getProduct(Long id);

    boolean addProduct(ProductsDTO productsDTO);

    void deleteProductById(Long id);

    ProductsDTO updateProduct(ProductsDTO productsDTO);

    boolean decreaseStock(List<OrderItemDTO> orderItemDTOList);

}
