package com.mf.order.controller.vo;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class OrderVO {
    private Long id;

    private String productName;

    private Integer count;

    private Date createTime;

    private Date updateTime;
}
