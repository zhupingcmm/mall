package com.mf.mall.user.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "customer-data")
public class AppProperties {

    @Getter
    @Setter
    private Jwt jwt = new Jwt();

    @Data
    public static class Jwt {
        private String header = "Authorization";
        private String prefix = "Bearer";
        private String jwtId = "mall-user";
        private String authorities = "authorities";
        private int id = 1;

        private long accessTokenExpireTime = 1000L; // Access Token 过期时间


    }
}
