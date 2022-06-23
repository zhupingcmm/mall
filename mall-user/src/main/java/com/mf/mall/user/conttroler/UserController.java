package com.mf.mall.user.conttroler;

import com.mf.mall.common.base.BaseResponse;
import com.mf.mall.common.config.JwtConfig;
import com.mf.mall.common.dto.UserDto;
import com.mf.mall.common.util.JwtUtil;
import com.mf.mall.user.config.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private AppProperties appProperties;
    @GetMapping("/hello")
    public String sayHello(HttpServletRequest request){
        val session = request.getSession();
        return session.getId();
    }

    @PostMapping("/token")
    public BaseResponse<String> createToken(@RequestBody UserDto userDto) {
        val jwtConfig= JwtConfig.builder()
                .jwtId(appProperties.getJwt().getJwtId())
                .accessTokenExpireTime(appProperties.getJwt().getAccessTokenExpireTime())
                .authorities(appProperties.getJwt().getAuthorities())
                .userDto(userDto)
                .build();
        return BaseResponse.success(JwtUtil.createToken(jwtConfig));
    }
    @GetMapping("/user/{id}")
    public BaseResponse<UserDto> getUserById(@PathVariable String id){
        System.out.println(id);

        return BaseResponse.success(UserDto.builder().name("z").build());
    }
}
