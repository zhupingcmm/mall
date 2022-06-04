package com.mf.mall.common.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class BaseResponse<T> extends BaseBean {
    private int code;
    private String message;
    private T data;

    private static final String EMPTY_STRING = StringUtils.EMPTY;

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResponse(ResponseEnum responseEnum) {
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }

    public static<T> BaseResponse<T> success(T data) {
        BaseResponse<T> response = new BaseResponse<T>(ResponseEnum.SUCCESS);
        response.setData(data);
        return response;
    }



    /**
     * 返回成功响应
     *
     * @return
     */
    public static BaseResponse success() {
        return success(EMPTY_STRING);
    }



    /**
     * 返回失败响应
     *
     * @return
     */
    public static BaseResponse error() {
        return new BaseResponse(ResponseEnum.ERROR);
    }

}
