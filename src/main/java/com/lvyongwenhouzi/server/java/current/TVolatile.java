package com.lvyongwenhouzi.server.java.current;

import lombok.Data;
import lombok.ToString;


/**
 * <b>volatile 修饰符测试</b>
 *
 *
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
