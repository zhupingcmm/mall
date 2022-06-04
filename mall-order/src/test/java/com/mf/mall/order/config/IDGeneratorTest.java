package com.mf.mall.order.config;

import com.mf.mall.common.base.IDTypeEnum;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IDGeneratorTest {
    @Resource
    private IDGenerator idGenerator;
//
    private static final int concurrency = 1;
    private final CountDownLatch countDownLatch = new CountDownLatch(concurrency);

    @Test
    public void testGenerateOrderNo() {
        for (int i = 0; i < 10; i++) {
            Long result = idGenerator.incr(IDTypeEnum.PRODUCTS);
            System.out.println(result);
        }
    }

    @SneakyThrows
    @Test
    public void concurrencyTesting() {
        for (int i = 0; i < concurrency; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                try {
//                    countDownLatch.await();
                    Long result = idGenerator.incr(IDTypeEnum.PRODUCTS);
                    System.out.println(finalI);
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            thread.start();
//            countDownLatch.countDown();
        }

//        Thread.sleep(100);
    }

}
