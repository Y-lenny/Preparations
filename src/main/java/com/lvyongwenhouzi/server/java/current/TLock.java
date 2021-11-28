package com.lvyongwenhouzi.server.java.current;

import java.util.concurrent.locks.ReentrantLock;

/**
 * <b>锁实现原理</b>
 * synchronized
 * synchronized 同步语句块的实现使用的是 monitorenter 和 monitorexit 指令，其中 monitorenter 指令指向同步代码块的开始位置，monitorexit 指令则指明同步代码块的结束位置。
 * synchronized 修饰的方法并没有 monitorenter 指令和 monitorexit 指令，取得代之的确实是 ACC_SYNCHRONIZED 标识，该标识指明了该方法是一个同步方法。
 * 不过两者的本质都是对对象监视器 monitor（objectMonitor.hpp） 的调用。
 * <p>
 * Lock(http://ifeve.com/aqs/)
 * 锁的实现：
 * 1、基于 AQS 实现了锁机制，AQS则是通过CLH(FIFO) 阻塞队列、volatile、cas、LockSupport.park/unpark等特性完成了在资源共享模式下实现线程安全访问。
 * 条件的实现：
 * 1、基于多条件队列 -> 阻塞队列的转换过程实现了多条件下的线程等待/唤醒通信能力。
 * <p>
 * 公平/非公平的区别：
 * 1、公平锁在进行锁的获取时会判断队列是否已有等待线程，非公平锁不但不会而且在获取锁的时候前置做一次获取锁(cas)的逻辑。
 * 2、非公平锁性能更高但是存在线程饥饿的情况。
 * <p>
 * 总结下Condition和wait/notify的比较：
 * Condition可以精准的对多个不同条件进行控制，wait/notify只能和synchronized关键字一起使用，并且只能唤醒一个或者全部的等待队列；
 * Condition需要使用Lock进行控制，使用的时候要注意lock()后及时的unlock()，Condition有类似于await的机制，因此不会产生加锁方式而产生的死锁出现，同时底层实现的是park/unpark的机制，因此也不会产生先唤醒再挂起的死锁，
 * 一句话就是不会产生死锁，但是wait/notify会产生先唤醒再挂起的死锁。
 *
 * @author 11114396 lvyongwen
 * @date 2021-08-27 16:16
 * @since 1.0
 */
public class TLock {

    /**
     * 使用 volatile 可以禁止 JVM 的指令重排，保证在多线程环境下在获取单例初始化正常。
     */
    private volatile static TLock tLock = null;

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                aqsMethod();
            }
        }, "第一个").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                aqsMethod();
            }
        }, "第二个").start();

    }

    public static void aqsMethod() {

        ReentrantLock reentrantLock = new ReentrantLock();

        if (tLock == null) {
            reentrantLock.lock();
            try {
                if (tLock == null) {
                    tLock = new TLock();
                    System.out.println("thread info : " + Thread.currentThread().getName());
                }
            } finally {
                reentrantLock.unlock();
            }
        }
    }
}
