package com.mf.common;

import com.mf.common.base.BaseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Payment extends BaseBean {
    private Integer id;
    private String message;
}
