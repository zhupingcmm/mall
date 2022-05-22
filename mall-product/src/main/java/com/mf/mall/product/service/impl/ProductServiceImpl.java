package com.mf.mall.product.service.impl;

import com.mf.mall.common.util.ObjectTransformer;
import com.mf.mall.product.mapper.ProductsMapper;
import com.mf.mall.product.model.ProductsDO;
import com.mf.mall.product.service.IProductService;
import com.mf.mall.common.dto.ProductsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements IProductService {
    private final ProductsMapper productsMapper;

    @Override
    public ProductsDTO getProduct(Long id) {
        ProductsDO productsDO  = productsMapper.selectProductById(id);
        log.info("Success to get product info {} by {}", productsDO, id);

        return ObjectTransformer.transform(productsDO,ProductsDTO.class);
    }
}
