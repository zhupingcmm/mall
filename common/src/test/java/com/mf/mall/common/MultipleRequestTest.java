package com.mf.mall.common;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MultipleRequestTest {

    private OkHttpClient httpClient;

    @Before
    public void init () {
        httpClient = new OkHttpClient().newBuilder()
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    @Test
    public void testGetProductById() throws InterruptedException {
        int N = 10;
        CountDownLatch latch = new CountDownLatch(N);
        for (int i = 0; i < N; i++) {
            new Thread(() -> {
                try {
                    latch.await();
                    System.out.println("---> start " + Thread.currentThread().getName());
                    Request request = new Request.Builder()
                            .url("http://localhost:8086/products/1")
                            .header("Content-Type", "application/json").build();
                    try (Response response = httpClient.newCall(request).execute()){
                        System.out.println(response.body().string());
                    }
                    System.out.println("---> end " + Thread.currentThread().getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            latch.countDown();
        }

//        Thread.sleep(10000L);

    }
}
