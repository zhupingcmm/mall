package com.mf.mall.product;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.Before;
import org.junit.Test;

public class GuavaBloomFilterTest {
    private int size = 1000;

    private double fpp = 0.01;

    private BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size, fpp);

    @Before
    public void initBloomFilter() {
        for (int i = 0; i < size; i++) {
            bloomFilter.put(i);
        }
    }

    @Test
    public void testGuavaBloomFilter(){
        int count =0;
        for (int i = size; i < size*2; i++) {
            if (bloomFilter.mightContain(i)) {
                count ++;
                System.out.println(i + "误判了");
            }
        }
        System.out.println("误判个数：" +  count);
    }

}
