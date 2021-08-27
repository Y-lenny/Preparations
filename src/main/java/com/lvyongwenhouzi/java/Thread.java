package com.lvyongwenhouzi.java;

import lombok.SneakyThrows;

/**
 * <b>Thread 线程有哪些状态以及在生命周期中是如何流转的 ？</b>
 * <p>
 * 创建(NEW)， 新建 {@link java.lang.Thread}
 * 运行(RUNNABLE，运行中(RUNNING) ——调用{@link java.lang.Thread#yield()}——>就绪(READY))，调用{@link java.lang.Thread#start()}等
 * 超时等待(TIME_WAITING)，调用{@link java.lang.Thread#sleep(long)}等
 * 等待(WAITING)，调用{@link Object#wait()}等
 * 阻塞(BLOCKING)，进入同步代码块synchronized{}
 * 终止(TERMINATED)，调用{@link java.lang.Thread#run()}
 *
 * @author 11114396 lvyongwen
 * @date 2021-08-26 11:36
 * @since 1.0
 */
public class Thread {

    public static void main(String[] args) throws InterruptedException {

        java.lang.Thread.sleep(10000);

        java.lang.Thread thread = new java.lang.Thread(new RunnableT());
        thread.start();

        java.lang.Thread.sleep(100000);
    }

    /**
     * 调用{@link java.lang.Thread#run()}
     */
    static class RunnableT implements Runnable {

        @SneakyThrows
        @Override
        public void run() {
            java.lang.Thread.sleep(10000);
            System.out.println("run线程！");
        }
    }


}
