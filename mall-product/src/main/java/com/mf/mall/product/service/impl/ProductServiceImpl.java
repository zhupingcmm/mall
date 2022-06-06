package com.mf.mall.product.service.impl;

import com.mf.mall.common.base.Constants;
import com.mf.mall.common.base.ResponseEnum;
import com.mf.mall.common.dto.OrderItemDTO;
import com.mf.mall.common.exception.BusinessException;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    @MyCachePut(cacheNames = Constants.PRODUCT_CHANGE_KEY_PRE, key = "#productsDTO.id")
    public ProductsDTO updateProduct(ProductsDTO productsDTO) {
        ProductsDO productsDO = ObjectTransformer.transform(productsDTO, ProductsDO.class);
        int result = productsMapper.updateProduct(productsDO);
        Assert.singleRowAffected(result);
        return productsDTO;
    }

    @Override
    public boolean decreaseStock(List<OrderItemDTO> orderItemDTOList) {

        List<ProductsDO> productsDOList = orderItemDTOList.stream().map(orderItemDTO -> ProductsDO.builder()
                .id(orderItemDTO.getGoodsId())
                .stock(orderItemDTO.getNumber())
                .build()).collect(Collectors.toList());
//        int result = productsMapper.updateProductsListStock(productsDOList);
//        if (result != productsDOList.size()) {
//            // 扣减成功的数量，与订单明细数量不一致，说明库存扣减有问题
//            throw new BusinessException(ResponseEnum.GOODS_STOCK_NOT_ENOUGH);
//        }
        return true;
    }
}
