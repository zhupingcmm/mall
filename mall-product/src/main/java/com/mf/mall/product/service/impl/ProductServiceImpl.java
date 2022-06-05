package com.mf.mall.product.service.impl;

import com.mf.mall.common.base.Constants;
import com.mf.mall.common.util.Assert;
import com.mf.mall.common.util.ObjectTransformer;
import com.mf.mall.product.aspect.MyCacheEvict;
import com.mf.mall.product.aspect.MyCachePut;
import com.mf.mall.product.aspect.MyCacheable;
import com.mf.mall.product.mapper.ProductsMapper;
import com.mf.mall.product.model.ProductsDO;
import com.mf.mall.product.service.IProductService;
import com.mf.mall.common.dto.ProductsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements IProductService {
    private final ProductsMapper productsMapper;
    private final RedisTemplate<String,ProductsDO> redisTemplate;

//    @Cacheable(cacheNames = Constants.PRODUCT_CHANGE_KEY, key = "#id")

    @Override
    @MyCacheable(cacheNames = Constants.PRODUCT_CHANGE_KEY_PRE, key = "#id")
    public ProductsDTO getProduct(Long id) {
        ProductsDO  productsDO  = productsMapper.selectProductById(id);
        log.info("Get productsDO: {}", productsDO);
        Assert.notNull(productsDO);
        return ObjectTransformer.transform(productsDO,ProductsDTO.class);
    }



    @Override
    @Transactional
    public boolean addProduct(ProductsDTO productsDTO) {
        ProductsDO productsDO = ObjectTransformer.transform(productsDTO, ProductsDO.class);
        int result = productsMapper.insertProduct(productsDO);
        return Assert.singleRowAffected(result);
    }

    @Override
    @MyCacheEvict(cacheNames = Constants.PRODUCT_CHANGE_KEY_PRE, key = "#id")
    public void deleteProductById(Long id) {
        productsMapper.deleteProductById(id);
        log.info("success to delete {}", id);
    }

    @Override
    @MyCachePut(cacheNames = Constants.PRODUCT_CHANGE_KEY_PRE, key = "#id")
    public boolean updateProduct(ProductsDTO productsDTO) {
        ProductsDO productsDO = ObjectTransformer.transform(productsDTO, ProductsDO.class);
        int result = productsMapper.updateProduct(productsDO);
        return Assert.singleRowAffected(result);
    }
}
