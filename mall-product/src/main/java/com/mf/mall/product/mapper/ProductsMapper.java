package com.mf.mall.product.mapper;

import com.mf.mall.product.model.ProductsDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductsMapper {

    ProductsDO selectProductById(Long id);

    int insertProduct(ProductsDO productsDO);

    void deleteProductById(Long id);

    int updateProduct(ProductsDO productsDO);

    /**
     * 批量更新商品库存
     * @param productsDOList
     * @return
     */

    int updateProductsListStock(List<ProductsDO> productsDOList);
}
