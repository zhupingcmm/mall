package com.mf.mall.product.exception;

import com.mf.mall.common.base.BaseResponse;
import com.mf.mall.common.base.ResponseEnum;
import com.mf.mall.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public BaseResponse exceptionHandler(HttpServletRequest request, Exception exception){
        if (exception instanceof BusinessException) {
            log.debug("BusinessException:", exception);
            BusinessException e = (BusinessException) exception;
            ResponseEnum responseEnum = e.getResponseEnum();
            if (responseEnum == null) {
                return new BaseResponse(e.getCode(), e.getMessage());
            } else {
                return new BaseResponse(responseEnum);
            }
        } else {
            log.error("System error:", exception);
            return BaseResponse.error();
        }
    }

}
