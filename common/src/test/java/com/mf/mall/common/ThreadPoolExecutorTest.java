package com.mf.mall.common;

import com.google.common.collect.Queues;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");

    public void execute(ThreadPoolExecutor threadPoolExecutor) {
        //提交15任务
        for (int i = 0; i < 15; i++) {
            int j = i;
            threadPoolExecutor.submit(() -> {
               try {
                   System.out.println(sdf.format(new Date()) + "\ttask-" + j + "\tstart");
                   Thread.sleep(2000L);
                   System.out.println(sdf.format(new Date()) + "\ttask-" + j + "\tend");
               } catch (Exception e) {
                   e.printStackTrace();
               }
            });
            System.out.println(sdf.format(new Date()) + "\ttask-" + j + "\tsubmit");

        }

        try {
            Thread.sleep(200L);
            System.out.println(sdf.format(new Date()) + "\t=== thread pool size:" + threadPoolExecutor.getPoolSize());
            System.out.println(sdf.format(new Date()) + "\t=== queue size:" + threadPoolExecutor.getQueue().size());
            Thread.sleep(20000L);
            System.out.println(sdf.format(new Date()) + "\t=== thread pool size:" + threadPoolExecutor.getPoolSize());
            System.out.println(sdf.format(new Date()) + "\t=== queue size:" + threadPoolExecutor.getQueue().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * coreSize = 5
     * maximumPoolSize = 10
     * keepAliveTime =5s
     * workQueue = 无界队列
     *
     */
    @Test
    public void testThreadPoolExecutor() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                5, 10,
                5, TimeUnit.SECONDS,
                Queues.newLinkedBlockingQueue()
        );

        execute(threadPoolExecutor);
    }

    @Test
    public void testFixedThreadPool() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                5, 5,
                0L, TimeUnit.SECONDS,
                Queues.newLinkedBlockingQueue()
        );
        execute(threadPoolExecutor);

//        Executors.newFixedThreadPool(5);
    }

    @Test
    public void testRejectedExecutionHandler(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                5, 10,
                5, TimeUnit.SECONDS,
                Queues.newLinkedBlockingQueue(2),
                (runnable, executor) -> {
                    System.err.println(runnable.toString() + " rejected!");
                }
        );
        execute(threadPoolExecutor);
    }

}
