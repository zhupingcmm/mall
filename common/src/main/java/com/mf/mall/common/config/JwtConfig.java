package com.mf.mall.common.config;

import com.mf.mall.common.base.BaseBean;
import com.mf.mall.common.dto.UserDto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JwtConfig extends BaseBean {
    private String jwtId = "mall-user";
    private String authorities = "authorities";
    private long accessTokenExpireTime;
    private UserDto userDto;
}
