package com.mf.mall.common.base;

import lombok.Getter;

public enum IDTypeEnum {
    USER  (1, "USER-ID"),
    PRODUCTS (2, "PRODUCTS-ID"),
    ORDER (3, "ORDER-ID"),
    COUPON(4, "COUPON-ID");

    IDTypeEnum(int code, String redisCounter) {
        this.code = code;
        this.redisCounter = redisCounter;
    }
    @Getter
    private int code;
    @Getter
    private String redisCounter;
}
