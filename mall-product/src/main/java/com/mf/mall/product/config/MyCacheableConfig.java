package com.mf.mall.product.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "mycacheable")
public class MyCacheableConfig {

    @Getter
    @Setter
    RateLimit rateLimit = new RateLimit();

    @Getter
    @Setter
    public static class RateLimit {
        private Map<String, Double> map = new HashMap<>();
    }

}
