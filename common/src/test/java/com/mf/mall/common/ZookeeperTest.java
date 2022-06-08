package com.mf.mall.common;

import lombok.val;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ZookeeperTest {
    private static final String PATH = "/products";
    private static final String TEMP_PATH = "/tempPath";
    private ZooKeeper zooKeeper;

    @Before
    public void init () throws IOException {
        zooKeeper = new ZooKeeper("localhost:2181", 5000, null);
    }

    @Test
    public void createNodeTest() throws InterruptedException, KeeperException {
        val result = zooKeeper.create(
                PATH,
                "createNodeTest".getBytes(StandardCharsets.UTF_8),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT
        );


        Assert.assertEquals(result, PATH );
    }

    @Test
    public void getChildrenTest() throws InterruptedException, KeeperException {
        val children = zooKeeper.getChildren(PATH, null);

        System.out.println(children);
        Assert.assertEquals(children.size(), 2);
    }

    @Test
    public void createTempNodeTest() throws InterruptedException, KeeperException {
        val result = zooKeeper.create(
                TEMP_PATH,
                "createNodeTest".getBytes(StandardCharsets.UTF_8),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL
        );
        Assert.assertEquals(result, TEMP_PATH );
    }

    @After
    public void tearDown() throws InterruptedException {
        zooKeeper.close();
    }


    @Test
    public void setDataTest() throws InterruptedException, KeeperException {
        val result = zooKeeper.setData(PATH, "zp".getBytes(StandardCharsets.UTF_8), 0);
    }

    @Test
    public void deleteNodeTest() throws InterruptedException, KeeperException {
        zooKeeper.delete(PATH + "/c1", 0);
    }

    @Test
    public void TestGetChildrenWithWatcher() throws InterruptedException, KeeperException {
        val result = zooKeeper.getChildren(PATH, event->{
            System.out.println(event);
        });
        val data = zooKeeper.getData(PATH, false, null);
        System.out.println(data);

        zooKeeper.delete(PATH + "/c1", 0);
    }

}
