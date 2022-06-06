package com.mf.mall.product.model;

import com.mf.mall.common.base.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsDO extends BaseBean {

    private Long id;

    private String name;

    private String description;

    private Integer type;

    private BigDecimal price;

    private Long stock;

    private Date createTime;

    private Date updateTime;

}
