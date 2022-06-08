package com.mf.mall.order.config;

import org.apache.zookeeper.ZooKeeper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ZookeeperConfig {
    @Bean(destroyMethod = "close")
    public ZooKeeper createZookeeper() throws IOException {
        return new ZooKeeper("localhost:2181", 5000, null);
    }
}
