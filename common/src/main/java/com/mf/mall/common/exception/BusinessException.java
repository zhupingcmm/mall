package com.mf.mall.common.exception;

import com.mf.mall.common.base.ResponseEnum;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private int code;
    private String message;
    private ResponseEnum responseEnum;

    public BusinessException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
    }

}
