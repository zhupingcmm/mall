package com.mf.mall.product.service.impl;

import com.mf.mall.common.base.Constants;
import com.mf.mall.common.util.Assert;
import com.mf.mall.common.util.JSONUtil;
import com.mf.mall.common.util.ObjectTransformer;
import com.mf.mall.product.mapper.ProductsMapper;
import com.mf.mall.product.model.ProductsDO;
import com.mf.mall.product.service.IProductService;
import com.mf.mall.common.dto.ProductsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements IProductService {
    private final ProductsMapper productsMapper;

    @Override
    public ProductsDTO getProduct(Long id) {
        ProductsDO productsDO;
        try (Jedis jedis = new Jedis("localhost", 6379)){
            String cacheKey = String.format(Constants.PRODUCTS_CACHE_KEY, id);
            String cacheValue = jedis.get(cacheKey);
            log.info("cache key: {}, value: {}", cacheKey, cacheValue);

            if (StringUtils.isBlank(cacheValue)) {
                productsDO  = productsMapper.selectProductById(id);
                Assert.notNull(productsDO);
                log.info("Success to get product info {} by {}", productsDO, id);
                jedis.set(cacheKey, JSONUtil.toJSONString(productsDO));
            } else {
                productsDO = JSONUtil.parseObject(cacheValue, ProductsDO.class);
            }
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
