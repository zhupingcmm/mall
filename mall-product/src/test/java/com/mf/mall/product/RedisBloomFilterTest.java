package com.mf.mall.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class RedisBloomFilterTest {
    private int size = 1000;

    private static final String BLOOM_FILTER_NAME = "goodsBloomFilter";

    @Autowired(required = true)
    private RedisTemplate<String, Long> redisTemplate;

    @Before
    public void init(){
        for (int i = 0; i < size; i++) {
            redisTemplate.opsForValue().setBit(BLOOM_FILTER_NAME, this.getOffset(i), true);
        }
    }

    @Test
    public void testCustomerBloomFilter() {
        int count = 0;
        for (int i = size; i < size*2; i++) {
            boolean match = redisTemplate.opsForValue().getBit(BLOOM_FILTER_NAME, this.getOffset(i));
            if (match){
                count++;
                System.out.println(i + "误判了");
            }
        }

        System.out.println("误判个数：" + count);

    }

    private long getOffset(int i) {
        long hashCode = Math.abs((BLOOM_FILTER_NAME + i).hashCode());
        long offset = (long) (hashCode%Math.pow(2,32));

        System.out.println(
                "index = " + i + "\t" +
                "hashCode = " + hashCode + "\t" +
                        "offset =" + offset
                );
        return  offset;
    }

}
