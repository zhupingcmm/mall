package com.mf.mall.common.retalimit;

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class Counter {
    private static final int permit = 1;
    private int count = 0;
    private long start;
    private long end;
//    ScheduledThreadPoolExecutor
    public Counter(){
        start = System.currentTimeMillis();
        end = start + 1000;
    }






}
