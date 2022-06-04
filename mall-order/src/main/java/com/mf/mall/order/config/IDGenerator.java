package com.mf.mall.order.config;

import com.mf.mall.common.base.IDTypeEnum;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class IDGenerator {
    private final RedisTemplate redisTemplate;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyMMddHHmmssSSS");

    /**
     * 根据业务类型生成ID 规则
     * 1位业务编码 + 15位日期 + 3位序列号
     * @param idTypeEnum 业务类型
     * @return
     */
    public Long incr(IDTypeEnum idTypeEnum){
        LocalDateTime now = LocalDateTime.now();
        String dateTime = dateTimeFormatter.format(now);

        long value = redisTemplate.opsForValue().increment(idTypeEnum.getRedisCounter(), 1);
        if (value >= 1000) {
            // 0 ~ 999
            value = value % 1000;
        }
        String seq = StringUtils.leftPad(Long.toString(value), 3, "0");

        // 业务代码 + 日期时间 + 序列号
        String result = idTypeEnum.getCode() + dateTime + seq;

        return  Long.parseLong(result);
    }


}
