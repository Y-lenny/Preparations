package com.lvyongwenhouzi.server.java.current;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <b>线程池创建方式流程</b>
 *
 * 1、判断最小线程数是否达到，没有达到新增任务时创建新线程
 * 2、判断队列容量是否达到，没有达到新增任务时加入到队列中
 * 3、判断最大线程数是否达到，没有达到新增任务时创建新线程
 * 4、否则使用拒绝策略
 *
 * <b>线程池调优路径<b/>
 *  任务每秒数量：tasksCount
 *  任务执行时间：taskCost
 *  任务有效时间：availableCost
 *
 *  一、调优参数有哪些 ？
 *  1、核心线程数（coreSize）   空闲时保留的最小线程数
 *  Nthreads=Ncpu∗Ucpu∗(1+W/C)
 *  Ncpu：表示处理器数量，可以通过Runtime.getRuntime().avaliableProcessors()获得；
 *  Ucpu：CPU的使用率，0⩽Ucpu⩽1；
 *  W/C：等待时间与计算时间的比值；
 *  另：coreSize = tasksCount/(1/taskCost)
 *
 *  2、最大线程数（maxSize）    线程池最大可创建/使用线程数
 *  Nthreads=Ncpu∗Ucpu∗(1+W/C)
 *  Ncpu：表示处理器数量，可以通过Runtime.getRuntime().avaliableProcessors()获得；
 *  Ucpu：CPU的使用率，0⩽Ucpu⩽1；
 *  W/C：等待时间与计算时间的比值；
 *  另：maxSize = (max(tasksCount)-capacity)/(1/taskCost)
 *
 *  3、线程存活时间（keepAliveTime） 空闲线程存活时间
 *  为了应对突增的量，一般时间为10分钟到30分钟
 *
 *  4、线程缓冲队列（workQueue） 当线程数不足时，缓冲任务的队列大小
 *  同步队列：不使用最大线程/队列缓冲并且可以允许任务直接拒绝
 *  无界队列：不使用拒绝策略
 *  有界队列：使用最大线程/队列缓冲/决策策略
 *  capacity = (coreSize/taskCost)*availableCost 代表缓冲队列任务必须在有效时间内执行的内执行。
 *
 *  5、任务处理策略（handler）   当线程和队列不足时，选择的处理策略
 *  抛出异常：（默认）
 *  主线程运行：
 *  直接丢弃：
 *  丢弃队列最早未处理任务：（不适合与优先级队列一起使用）
 *
 *  二、其他调优场景
 *  1、任务类型（优先级、执行耗时、CPU密集型/IO密集型、依赖关系）不同尽量不使用同一个线程池 —— 避免相互影响
 *  2、动态化线程池参数 —— 方便调优、按照业务流量动态调整
 *
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
