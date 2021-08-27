package com.lvyongwenhouzi.java;

import java.util.HashMap;

/**
 * <b>Map的关键技术点 ？</b>
 *
 * 1、hash() 方法
 * 2、add() 方法
 *
 * @author 11114396 lvyongwen
 * @date 2021-08-24 17:39
 * @since 1.0
 */
public class TMap {

    public static void main(String[] args) throws InterruptedException {
        int cap = 100;
        int n = cap - 1;
        // 让最高位的1后面全部补1（确保是奇数），最后+1再变成偶数
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;

        java.lang.Thread.sleep(100000000);
        System.out.println(n + 1); // 128
    }

}
