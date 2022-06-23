package com.mf.mall.common.util;

import com.mf.mall.common.config.JwtConfig;
import com.mf.mall.common.dto.UserDto;
import lombok.val;
import org.junit.Test;

public class JwtUtilTest {
    @Test
    public void testCreateToken () {
        UserDto userDto = UserDto.builder()
                .name("zp")
                .password("11111")
                .build();
        JwtConfig jwtConfig = JwtConfig.builder()
                .userDto(userDto)
                .build();
       String token =  JwtUtil.createToken(jwtConfig);
       val isValid = JwtUtil.validateToken(token);
       JwtUtil.parseClaims(token).map(claims -> {
           System.out.println(claims);
           return claims;
       });
       System.out.println( isValid);

    }
}
