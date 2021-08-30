package com.lvyongwenhouzi.server.java;

import java.util.concurrent.locks.ReentrantLock;

/**
 * <b>锁实现原理</b>
 * synchronized
 * synchronized 同步语句块的实现使用的是 monitorenter 和 monitorexit 指令，其中 monitorenter 指令指向同步代码块的开始位置，monitorexit 指令则指明同步代码块的结束位置。
 * synchronized 修饰的方法并没有 monitorenter 指令和 monitorexit 指令，取得代之的确实是 ACC_SYNCHRONIZED 标识，该标识指明了该方法是一个同步方法。
 * 不过两者的本质都是对对象监视器 monitor（objectMonitor.hpp） 的调用。
 * <p>
 * Lock
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


    public static void syncMethod() {

        /**
         *     Code:
         *       stack=3, locals=2, args_size=0
         *          0: getstatic     #11                 // Field tLock:Lcom/lvyongwenhouzi/java/TLock;
         *          3: ifnonnull     67
         *          6: ldc           #12                 // class com/lvyongwenhouzi/java/TLock
         *          8: dup
         *          9: astore_0
         *         10: monitorenter                      // 监听锁开始，线程试图获取锁也就是获取 对象监视器 monitor 的持有权（如果锁的计数器为 0 则表示锁可以被获取，获取后将锁计数器设为 1 也就是加 1）
         *         11: getstatic     #11                 // Field tLock:Lcom/lvyongwenhouzi/java/TLock;
         *         14: ifnonnull     57
         *         17: new           #12                 // class com/lvyongwenhouzi/java/TLock
         *         20: dup
         *         21: invokespecial #13                 // Method "<init>":()V
         *         24: putstatic     #11                 // Field tLock:Lcom/lvyongwenhouzi/java/TLock;
         *         27: getstatic     #14                 // Field java/lang/System.out:Ljava/io/PrintStream;
         *         30: new           #15                 // class java/lang/StringBuilder
         *         33: dup
         *         34: invokespecial #16                 // Method java/lang/StringBuilder."<init>":()V
         *         37: ldc           #17                 // String thread info :
         *         39: invokevirtual #18                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
         *         42: invokestatic  #19                 // Method java/lang/Thread.currentThread:()Ljava/lang/Thread;
         *         45: invokevirtual #20                 // Method java/lang/Thread.getName:()Ljava/lang/String;
         *         48: invokevirtual #18                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
         *         51: invokevirtual #21                 // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
         *         54: invokevirtual #22                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
         *         57: aload_0
         *         58: monitorexit                       // 监听锁结束，将锁计数器设为 0，表明锁被释放。
         *         59: goto          67
         *         62: astore_1
         *         63: aload_0
         *         64: monitorexit                       // monitorexit指令出现了两次，第1次为同步正常退出释放锁；第2次为发生异步退出释放锁；
         *         65: aload_1
         *         66: athrow
         *         67: return
         */
        if (tLock == null) {
            synchronized (TLock.class) {
                if (tLock == null) {
                    tLock = new TLock();
                    System.out.println("thread info : " + Thread.currentThread().getName());
                }
            }
        }
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
