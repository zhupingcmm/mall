package com.mf.mall.product.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.concurrent.CountDownLatch;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;



    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetProductById() throws Exception {
        int concurrency = 2;
        CountDownLatch countDownLatch = new CountDownLatch(concurrency);
        for (int i = 0; i < concurrency; i++) {

            new Thread(() -> {
                ResultActions result = null;
                try {
                    countDownLatch.await();
                    result = mockMvc.perform(MockMvcRequestBuilders.get("/products/1"));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                System.out.printf("result",result);
            }).start();
            countDownLatch.countDown();
        }


    }
}
