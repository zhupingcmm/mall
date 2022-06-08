package com.mf.mall.order.service.impl;

import com.mf.mall.common.base.Constants;
import com.mf.mall.order.service.IZookeeperService;
import lombok.RequiredArgsConstructor;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class ZookeeperServiceImpl implements IZookeeperService {

    private final ZooKeeper zooKeeper;

    @PostConstruct
    private void register() throws InterruptedException, KeeperException {
        zooKeeper.create(Constants.SERVICE_PATH, "http://localhost:8848/".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }
}
