package com.mf.mall.product.service.impl;

import com.mf.mall.product.service.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@SpringBootTest
@RunWith(SpringRunner.class)
public class HelloServiceImplTest {
    @Autowired
    private HelloService helloService;
    @Test
    public void contextLoad(){
        helloService.sayHi();
    }
}
