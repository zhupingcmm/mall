package com.mf.gateway.config;

import com.mf.gateway.filter.OneGatewayFilter;
import com.mf.gateway.filter.ThreeGatewayFilter;
import com.mf.gateway.filter.TwoGatewayFilter;
import com.mf.gateway.filter.URLValidateFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {



    @Bean
    public RouteLocator someRouteLocator(RouteLocatorBuilder builder) {
        //路由构建器对象，构建一个路由规则
        return builder.routes()
                .route("custom_filter_route",ps -> ps.path("/payment/**")
                        .filters(gfs -> gfs
                                .filter(new OneGatewayFilter())
                                .filter(new TwoGatewayFilter())
                                .filter(new ThreeGatewayFilter()))
                        .uri("http://localhost:8080"))
                .build();
    }

//    @Bean
//    public RouteLocator globalRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("custom_global_filter_route",ps -> ps.path("/**")
//                        .uri("http://localhost:8080"))
//                .build();
//    }

}
