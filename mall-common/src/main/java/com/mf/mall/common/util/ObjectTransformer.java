package com.mf.mall.common.util;

import com.mf.mall.common.base.ResponseEnum;
import com.mf.mall.common.exception.BusinessException;
import org.springframework.beans.BeanUtils;

public class ObjectTransformer {

    public static <T> T transform(Object source, Class<T> t){
        return transform(source,t, null);
    }

    public static <T> T transform(Object source, Class<T> t, ObjectTransformAfter<T> after) {
        T target;
        try {
            target = t.newInstance();
            BeanUtils.copyProperties(source, target);
            if (after != null) {
                after.after(source, target);
            }
        } catch (InstantiationException | IllegalAccessException e) {
           throw new BusinessException(ResponseEnum.TRANSFORM_EXCEPTION);
        }
        return target;
    }
}
