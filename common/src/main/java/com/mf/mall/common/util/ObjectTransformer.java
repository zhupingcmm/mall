package com.mf.mall.common.util;

import com.mf.mall.common.base.ResponseEnum;
import com.mf.mall.common.exception.BusinessException;
import org.apache.commons.beanutils.BeanUtils;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ObjectTransformer {

    public static <T> T transform(Object source, Class<T> t){
        return transform(source,t, null);
    }

    public static <T> T transform(Object source, Class<T> t, ObjectTransformAfter<T> after) {
        T target = null;
        try {
            target = t.newInstance();
            BeanUtils.copyProperties(source, target);
            if (after != null) {
                after.after(source, target);
            }
        } catch (InstantiationException | IllegalAccessException e) {
           throw new BusinessException(ResponseEnum.TRANSFORM_EXCEPTION);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return target;
    }

    public static <T> List<T> transform(List<?> list, Class<T> t) {
        if (null == list) {
            return new ArrayList<>();
        }
        List<T> resultList = new ArrayList<>(list.size());
        for (Object element : list) {
            resultList.add(transform(element, t));
        }
        return resultList;
    }
}
