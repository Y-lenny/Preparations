package com.lvyongwenhouzi.java;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <b>线程池创建方式 ？</b>
 *
 * 1、判断最小线程数是否达到，没有达到新增任务时创建新线程
 * 2、判断队列容量是否达到，没有达到新增任务时加入到队列中
 * 3、判断最大线程数是否达到，没有达到新增任务时创建新线程
 * 4、否则使用拒绝策略
 *
 * @author 11114396 lvyongwen
 * @date 2021-08-27 14:11
 * @since 1.0
 */
public class TThreadPoolExecutor {

    private static ThreadPoolExecutor threadPoolExecutor;

    private static AtomicInteger atomicInteger = new AtomicInteger();

    static {
        int size = 2 * Runtime.getRuntime().availableProcessors();
        threadPoolExecutor = new ThreadPoolExecutor(size, size, 0L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(100), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static void main(String[] args) {

        threadPoolExecutor.execute(new MRunnable());

        threadPoolExecutor.execute(new MRunnable());

        threadPoolExecutor.execute(new MRunnable());
    }

    static class MRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("I am the " + atomicInteger.decrementAndGet() + " task to running");
        }
    }
}
