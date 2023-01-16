package com.mf.common.payment;

import com.mf.common.base.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PaymentVO extends BaseBean {

    private Long id;

    private String productName;

    private Integer count;

    private Double unitPrice;

    private Double amount;

    private Date createTime;

    private Date updateTime;
}
