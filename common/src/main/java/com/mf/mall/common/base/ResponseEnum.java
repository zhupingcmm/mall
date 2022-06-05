package com.mf.mall.common.base;

import lombok.Getter;

public enum ResponseEnum {
    SUCCESS(0, "交易成功"),

    /** 数据库操作异常 **/
    ENTITY_NOT_FOUND(1000, "数据不存在"),
    NO_ROWS_AFFECTED(1000, "数据未更新成功"),
    TOO_MANY_ROWS_AFFECTED(1001,"更新到其他数据"),
    NOT_TOTAL_ROWS_AFFECTED(1002,"数据查询或者更新不全"),

    /** 用户服务 2xxx **/

    /** 商品服务 3xxx **/
    GOODS_STOCK_NOT_ENOUGH(3001,"商品库存不足"),

    /** 订单服务 4xxx **/

    /** 优惠券服务 5xxx **/
    COUPON_NOT_ENOUGH(5001, "优惠券已被领取完"),
    COUPON_USED(5002, "优惠券已被使用"),
    COUPON_EXPIRED(5003, "优惠券已过期"),
    SYSTEM_ERROR(8888,"系统异常"),

    /** 程序异常 **/
    FEIGN_CALL_EXCEPTION(9997,"远程调用失败"),
    TRANSFORM_EXCEPTION(9998, "对象转换异常"),
    ERROR(9999, "系统异常");

    ResponseEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
    @Getter
    private int code;
    @Getter
    private String message;


}
