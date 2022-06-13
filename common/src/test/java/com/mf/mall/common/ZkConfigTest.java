package com.mf.mall.common;

import com.mf.mall.common.zk.ZkConfig;
import org.junit.Test;

public class ZkConfigTest {
    private static final String PATH = "/config";

    @Test
    public void testCreateConfig() {
        ZkConfig zkConfig = new ZkConfig();
        zkConfig.setConfigData(PATH, "backlist:123,456,789");
    }

    @Test
    public void testGetConfig() throws InterruptedException {
        ZkConfig zkConfig = new ZkConfig();
        zkConfig.getConfigData(PATH);
    }
}
