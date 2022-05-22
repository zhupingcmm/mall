package com.mf.mall.common.util;

import com.mf.mall.common.base.ResponseEnum;
import com.mf.mall.common.exception.BusinessException;

public class Assert<T> {
    public static boolean singleRowAffected(int result) {
        if (result == 0) {
            throw new BusinessException(ResponseEnum.NO_ROWS_AFFECTED);
        } else if( result > 1) {
            throw new BusinessException(ResponseEnum.TOO_MANY_ROWS_AFFECTED);
        }

        return true;
    }

    public static <T> boolean notNull (T data) {
        if (data == null) {
            throw new BusinessException(ResponseEnum.ENTITY_NOT_FOUND);
        }
        return true;
    }
}
