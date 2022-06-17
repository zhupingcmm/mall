package com.mf.mall.product.service.impl;

import com.mf.mall.product.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHi() {
        System.out.println("hello service");
    }
}
