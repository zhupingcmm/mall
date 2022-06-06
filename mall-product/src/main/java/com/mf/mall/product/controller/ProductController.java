package com.mf.mall.product.controller;

import com.mf.mall.common.base.BaseResponse;
import com.mf.mall.common.dto.OrderItemDTO;
import com.mf.mall.common.dto.ProductsDTO;
import com.mf.mall.product.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final IProductService productService;

    @PutMapping("/stock")
    public BaseResponse checkAndDecreaseStock(@RequestBody List<OrderItemDTO> orderItemDTOList) {
        log.info("orderItemDTOList, {}", orderItemDTOList);
        productService.decreaseStock(orderItemDTOList);
        return BaseResponse.success();
    }

    @GetMapping("/{id}")
    public BaseResponse<ProductsDTO> getProducts(@PathVariable Long id) {
        ProductsDTO productsDTO = productService.getProduct(id);
        return BaseResponse.success(productsDTO);
    }
    @DeleteMapping("/{id}")
    public BaseResponse deleteProduct(@PathVariable Long id){
        productService.deleteProductById(id);
        return BaseResponse.success();
    }

    @PutMapping
    public BaseResponse<ProductsDTO> updateProduct(@RequestBody ProductsDTO productsDTO){
        productService.updateProduct(productsDTO);
        return BaseResponse.success();
    }

    @PostMapping
    public BaseResponse addProduct(@RequestBody ProductsDTO productsDTO) {
        productService.addProduct(productsDTO);
        return BaseResponse.success();
    }

}
