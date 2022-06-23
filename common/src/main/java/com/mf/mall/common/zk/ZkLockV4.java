package com.mf.mall.common.zk;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ZkLockV4 implements Lock {

    private static final String zkServer = "localhost:2181";
    private ZkClient zkClient;

    private String lockPath;
    private ThreadLocal<String> currentPath = new ThreadLocal<>();
    private ThreadLocal<String> previousPath = new ThreadLocal<>();

    public ZkLockV4 (String lockPath) {
        this.lockPath = lockPath;
        zkClient = new ZkClient(zkServer);
        zkClient.setZkSerializer(new ZkSerializer());
        if (!zkClient.exists(lockPath)) {
            zkClient.createPersistent(lockPath);
        }
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

            zkClient.subscribeDataChanges(previousPath.get(), dataListener);

            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            zkClient.unsubscribeDataChanges(previousPath.get(), dataListener);
            lock();
        }

    }
    @Override
    public boolean tryLock() {
        if (StringUtils.isBlank(currentPath.get())) {
            System.out.println("----");
            String path = zkClient.createEphemeralSequential(lockPath + "/", Thread.currentThread().getName());
            currentPath.set(path);
        }


        List<String> children = zkClient.getChildren(lockPath);
        Collections.sort(children);
        if (StringUtils.equals(currentPath.get(), lockPath + "/" + children.get(0))) {
            return true;
        } else {
            int index = children.indexOf(currentPath.get().substring(lockPath.length() + 1));
            String path = lockPath + "/" + children.get(index - 1);
            previousPath.set(path);
            return false;
        }
    }

    @Override
    public void unlock() {
        String data = zkClient.readData(currentPath.get());
        if (StringUtils.equals(data, Thread.currentThread().getName())) {
            zkClient.delete(currentPath.get());
        }

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {}

    @Override
    public boolean tryLock(long time, @NotNull TimeUnit unit) throws InterruptedException {return false;}

    @NotNull
    @Override
    public Condition newCondition() {return null;}

    public static void main(String[] args) {

        int concurrency = 10;
        CountDownLatch countDownLatch = new CountDownLatch(concurrency);

        for (int i = 0; i < concurrency; i++) {
            new Thread(() -> {
                try {
                    countDownLatch.await();
                    ZkLockV4 zkLock = new ZkLockV4("/lock");
                    zkLock.lock();
                    System.out.println("*** " + Thread.currentThread().getName() + "获得了锁 ***");
                    zkLock.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            countDownLatch.countDown();
        }
    }
}
