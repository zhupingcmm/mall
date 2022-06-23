package com.mf.mall.common.zk;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ZkLockV3 implements Lock {

    private final static String zkServer = "localhost:2181";

    private ZkClient zkClient;

    private String lockPath;

    public ZkLockV3 (String lockPath){
        this.lockPath = lockPath;
        zkClient = new ZkClient(zkServer);
    }

    @Override
    public boolean tryLock() {
        try {
            zkClient.createEphemeral(lockPath, Thread.currentThread().getName());
        } catch (ZkNodeExistsException e) {
            return false;
        }
        return true;
    }

    @Override
    public void lock() {
        if (!tryLock()) {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            IZkDataListener dataListener = new IZkDataListener() {
                @Override
                public void handleDataChange(String s, Object o) throws Exception {

                }

                @Override
                public void handleDataDeleted(String s) throws Exception {
                    System.out.println("*** 锁释放了，可以重新获取锁了 ***");
                    countDownLatch.countDown();
                }
            };
            zkClient.subscribeDataChanges(lockPath, dataListener);
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            zkClient.unsubscribeDataChanges(lockPath, dataListener);

            lock();
        }

    }

    @Override
    public void unlock() {
        String data = zkClient.readData(lockPath);
        if (StringUtils.equals(data, Thread.currentThread().getName())){
            zkClient.delete(lockPath);
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }


    @Override
    public boolean tryLock(long time, @NotNull TimeUnit unit) throws InterruptedException {
        return false;
    }

    @NotNull
    @Override
    public Condition newCondition() {
        return null;
    }

    public static void main(String[] args) {
        int concurrency = 20;
        CountDownLatch countDownLatch = new CountDownLatch(concurrency);
        for (int i = 0; i < concurrency; i++) {
            new Thread(() -> {
                try {
                    countDownLatch.await();
                    ZkLockV3 v3 = new ZkLockV3("/ab");
                    v3.lock();
                    System.out.println("*** " + Thread.currentThread().getName() + "获得了锁 ***");
                    v3.unlock();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
            countDownLatch.countDown();
        }
    }
}
