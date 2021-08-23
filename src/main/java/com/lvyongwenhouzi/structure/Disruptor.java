package com.lvyongwenhouzi.structure;

/**
 * <b>剖析高性能队列Disruptor背后的数据结构和算法</b>
 *
 * @author 11114396 lvyongwen
 * @date 2021-08-23 19:57
 * @since 1.0
 */
public class Disruptor {


    public static void main(String[] args) {


    }


    /**
     * 基于循环队列的“生产者 - 消费者模型”
     */
    class CycleQueue {

        // 队列
        private int[] arr = new int[10];
        // 队首
        private int head;
        // 队尾
        private int tail;

        // 入队
        public boolean offer(int data) {

            // 判断队列是否已满
            if ((tail + 1) % arr.length == head) {
                return false;
            } else {

                tail = (tail + 1) ;

                return true;
            }
        }

        // 出队
        public Integer poll() {

            // 判断队列是否为空
            if (head == tail) {

            }


            return 0;
        }

    }


    /**
     * 基于加锁的并发“生产者 - 消费者模型”
     */


    /**
     * 基于无锁的并发“生产者 - 消费者模型”
     */


}
