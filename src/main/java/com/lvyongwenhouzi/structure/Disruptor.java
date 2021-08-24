package com.lvyongwenhouzi.structure;

import lombok.SneakyThrows;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * <b>剖析高性能队列Disruptor背后的数据结构和算法</b>
 *
 * @author 11114396 lvyongwen
 * @date 2021-08-23 19:57
 * @since 1.0
 */
public class Disruptor {

    private static final Executor executor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {

        CycleQueue cycleQueue = new CycleQueue();
        final CycleProducer cycleProducer = new CycleProducer(cycleQueue);
        final CycleConsumer cycleConsumer = new CycleConsumer(cycleQueue);

        executor.execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                int data = 0;
                cycleProducer.product(data++);
            }
        });
        executor.execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                cycleConsumer.consume();
            }
        });
    }


    /**
     * 基于循环队列的“生产者 - 消费者模型”
     */
    /**
     * 基于加锁的并发“生产者 - 消费者模型”
     */
    static class CycleQueue {

        // 队列
        private int[] arr = new int[10];
        // 队首
        private int head;
        // 队尾（指向空下标）
        private int tail;

        // 队列大小
        private int size = 10;

        // 入队
        public boolean offer(int data) {

            // 判断队列是否已满
            if ((tail + 1) % size == head) {
                return false;
            } else {
                arr[tail] = data;
                tail = (tail + 1) % size;
                return true;
            }
        }

        // 出队
        public Integer poll() {

            // 判断队列是否为空
            if (head == tail) {
                return null;
            }

            Integer retVal;
            retVal = arr[head];
            head = (head + 1) % size;
            return retVal;
        }

        public boolean isFull() {
            return (tail + 1) % size == head;
        }

        public boolean isEmpty() {
            return tail == head;
        }

    }

    static class CycleProducer {

        private CycleQueue cycleQueue;

        public CycleProducer(CycleQueue cycleQueue) {
            this.cycleQueue = cycleQueue;
        }

        public void product(int data) throws InterruptedException {
            synchronized (cycleQueue) {

                while (true) {
                    if (cycleQueue.isFull()) {
                        cycleQueue.wait();
                    }
                    if (cycleQueue.offer(data)) {
                        System.out.println("生产成功...");
                        cycleQueue.notify();
                    }
                }
            }
        }
    }

    static class CycleConsumer {

        private CycleQueue cycleQueue;

        public CycleConsumer(CycleQueue cycleQueue) {
            this.cycleQueue = cycleQueue;
        }

        public void consume() throws InterruptedException {

            synchronized (cycleQueue) {
                while (true) {
                    if (cycleQueue.isEmpty()) {
                        cycleQueue.wait();
                    }
                    if (cycleQueue.poll() != null) {
                        System.out.println("消费成功...");
                        cycleQueue.notify();
                    }
                }
            }
        }
    }


    /**
     * 基于无锁的并发“生产者 - 消费者模型”
     */



}
