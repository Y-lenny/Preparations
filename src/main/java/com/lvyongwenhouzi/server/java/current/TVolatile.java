package com.lvyongwenhouzi.server.java.current;

import lombok.Data;
import lombok.ToString;


/**
 * <b>volatile 修饰符测试</b>
 * 主要作用：
 *  1、数据可见性
 *  2、执行顺序性
 * 实现原理：
 *  1、可见性：因为为了满足CPU和内存的速度匹配度（虽然有寄存器但容量有限），提出了L1/2/3级的高速缓存；虽然在单核情况时代这种不一致情况还很少（排除DMA）
 *  但是多核的时代那么数据的不一致（高速缓存-内存）就尤为明显了；为了解决这种问题提出了MESI协议（Modify/Exclude/Shared/Invalid）又四态缓存写回无效协议，
 *  核心思想就是在多核CPU环境下缓存行数据进行读写操作会变更不同状态，并根据状态进行数据写回和失效操作。
 *  扩展：在mesi优化过程中提出了：存储缓存、失效队列方案但其只能达到数据最终一致性，对于大部分场景是适合的但是对于共享变量/内存则存在问题所以又提出了内存屏障，
 *  内存屏障可以认为是处理器指令，又分为：
 *      读屏障：写屏障用于保证高速缓存间写事务的强一致性。当CPU执行写屏障指令时，必须强制等待存储缓存中的写事务全部处理完再继续执行后面的指令。相当于将存储缓存中异步处理的本地写事务做了强一致的同步。
 *      写屏障：读屏障用于保证高速缓存间读事务的强一致性。当CPU执行读屏障指令时，必须先将当前处于失效队列中的写无效事务全部处理完，再继续的执行读屏障后面的指令。相当于将异步队列中异步处理的远程写事务做了强一致的同步。　
 *  硬件实现方式：
 *     1. lfence，是一种Load Barrier 读屏障
 *     2. sfence, 是一种Store Barrier 写屏障
 *     3. mfence, 是一种全能型的屏障，具备ifence和sfence的能力
 *     4. Lock前缀，Lock不是一种内存屏障，但是它能完成类似内存屏障的功能。Lock会对CPU总线和高速缓存加锁，可以理解为CPU指令级的一种锁。它后面可以跟ADD,ADC, AND, BTC, BTR, BTS, CMPXCHG, CMPXCH8B, DEC, INC, NEG, NOT, OR, SBB, SUB, XOR, XADD, and XCHG等指令。
 *  通过hsdis分析volatile关键字是通过lock前缀实现的。
 *
 *  2、顺序性：编译器和执行器操作时为了优化并行执行效率会进行指令重排序，在某些场景下会产生问题，比如单例模式-懒汉式（Singleton o = new Singleton() => 分配内存、初始化、赋值命令）中因为重排序了
 *  导致其他线程在获取对象时会产生获取到了未准备好的对象；基于这种问题编译器在生成字节码时，会在指令序列中插入内存屏障来禁止特定类型的处理器重排序。然而，对于编译器来说，
 *  发现一个最优布置来最小化插入屏障的总数几乎不可能，为此，Java内存模型采取保守策略。下面是基于保守策略的JMM内存屏障插入策略：
 *  在每个volatile写操作的前面插入一个StoreStore屏障。
 *  在每个volatile写操作的后面插入一个StoreLoad屏障。
 *  在每个volatile读操作的后面插入一个LoadLoad屏障。
 *  在每个volatile读操作的后面插入一个LoadStore屏障。
 *
 * @author 11114396 lvyongwen
 * @date 2021-11-24 20:00
 * @since 1.0
 */
public class TVolatile {

    public static void main(String[] args) {
        Person person = new Person();
        person.updateAge(10);
        System.out.println("person = " + person);
    }


    @Data
    @ToString
    public static class Person {

        /**
         * 年龄
         *   0x000000010b1046c8: lock addl $0x0,(%rsp)     ;*putfield age
         *                                                 ; - com.lvyongwenhouzi.server.java.current.TVolatile$Person::updateAge@14 (line 45)
         * 通过hsdis分析得出：在对「this.age = age;」进行赋值操作时，使用volatile修复的字段会有一个 lock 指令标识符；主要作用：
         *  将当前处理器缓存行的数据写回到系统内存。
         *  写回内存的操作会使在其他 CPU 里缓存了该内存地址的额数据无效。
         * 其遵从缓存一执行协议（MESI）
         */
        private volatile int age;
        /**
         * 姓名
         */
        private int name;

        public void updateAge(int age) {
            age++;
            age+=100;
            age-=100;
            age--;
            this.age = age;
        }
    }


}
