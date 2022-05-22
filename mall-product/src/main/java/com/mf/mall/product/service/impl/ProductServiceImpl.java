package com.mf.mall.product.service.impl;

import com.mf.mall.common.base.Constants;
import com.mf.mall.common.util.Assert;
import com.mf.mall.common.util.ObjectTransformer;
import com.mf.mall.product.mapper.ProductsMapper;
import com.mf.mall.product.model.ProductsDO;
import com.mf.mall.product.service.IProductService;
import com.mf.mall.common.dto.ProductsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements IProductService {
    private final ProductsMapper productsMapper;
    private final RedisTemplate<String,ProductsDO> redisTemplate;

    @Override
    public ProductsDTO getProduct(Long id) {
        String cacheKey = String.format(Constants.PRODUCTS_CACHE_KEY, id);
        ProductsDO productsDO = redisTemplate.opsForValue().get(cacheKey);
        log.info("cache key: {}, value: {}", cacheKey, productsDO);

        if (productsDO == null) {
            productsDO  = productsMapper.selectProductById(id);
            Assert.notNull(productsDO);
            log.info("Success to get product info {} by {}", productsDO, id);
            redisTemplate.opsForValue().set(cacheKey, productsDO);
        }

        return ObjectTransformer.transform(productsDO,ProductsDTO.class);
    }

    @Override
    @Transactional
    public boolean addProduct(ProductsDTO productsDTO) {
        ProductsDO productsDO = ObjectTransformer.transform(productsDTO, ProductsDO.class);
        int result = productsMapper.insertProduct(productsDO);
        return Assert.singleRowAffected(result);
    }
}
