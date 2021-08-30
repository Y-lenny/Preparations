package com.lvyongwenhouzi.server.structure;

import lombok.SneakyThrows;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <b>剖析高性能队列Disruptor背后的数据结构和算法</b>
 *
 * @author 11114396 lvyongwen
 * @date 2021-08-23 19:57
 * @since 1.0
 */
public class Disruptor {

    private static final Executor EXECUTOR = Executors.newFixedThreadPool(2);

    // 锁 synchronize
    private static final Lock LOCK = new ReentrantLock(true);
    // 条件 Object.wait/notify
    private static final Condition CONDITION = LOCK.newCondition();

    public static void main(String[] args) {

        CycleQueue cycleQueue = new CycleQueue();
        final LockCycleProducer cycleProducer = new LockCycleProducer(cycleQueue);
        final LockCycleConsumer cycleConsumer = new LockCycleConsumer(cycleQueue);

        EXECUTOR.execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                int data = 0;
                cycleProducer.product(data++);
            }
        });
        EXECUTOR.execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                cycleConsumer.consume();
            }
        });
    }


    /**
     * 基于循环队列+加锁(synchronized)的并发“生产者 - 消费者模型”
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
     * 基于循环队列+加锁(lock+condition)的并发“生产者 - 消费者模型”
     */
    static class LockCycleProducer {

        private CycleQueue cycleQueue;

        public LockCycleProducer(CycleQueue cycleQueue) {
            this.cycleQueue = cycleQueue;
        }

        public void product(int data) throws InterruptedException {

            LOCK.lock();
            try {
                while (true) {
                    if (cycleQueue.isFull()) {
                        CONDITION.await();
                    }
                    if (cycleQueue.offer(data)) {
                        System.out.println("生产成功...");
                        CONDITION.signal();
                    }
                }
            } finally {
                LOCK.unlock();
            }
        }
    }

    static class LockCycleConsumer {

        private CycleQueue cycleQueue;

        public LockCycleConsumer(CycleQueue cycleQueue) {
            this.cycleQueue = cycleQueue;
        }

        public void consume() throws InterruptedException {

            LOCK.lock();
            try {
                while (true) {
                    if (cycleQueue.isEmpty()) {
                        CONDITION.await();
                    }
                    if (cycleQueue.poll() != null) {
                        System.out.println("消费成功...");
                        CONDITION.signal();
                    }
                }
            } finally {
                LOCK.unlock();
            }
        }
    }

    /**
     * 基于无锁的并发“生产者 - 消费者模型”
     */


}
