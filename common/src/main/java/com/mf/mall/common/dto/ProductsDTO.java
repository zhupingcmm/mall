package com.mf.mall.common.dto;

import com.mf.mall.common.base.BaseBean;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ProductsDTO extends BaseBean {
    /** 商品ID **/
    private Long id;

    /** 商品名称 **/
    private String name;

    /** 商品描述 **/
    private String description;

    /** 商品类型 **/
    private Integer type;

    /** 商品价格 **/
    private BigDecimal price;

    /** 商品库存 **/
    private Long stock;
}
