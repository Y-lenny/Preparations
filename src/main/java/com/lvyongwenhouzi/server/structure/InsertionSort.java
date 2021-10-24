package com.lvyongwenhouzi.server.structure;

import java.util.Arrays;

/**
 * 首先，我们将数组中的数据分为两个区间，已排序区间和未排序区间。初始已排序区间只有一个元素，就是数组的第一个元素。
 * 插入算法的核心思想是取未排序区间中的元素，在已排序区间中找到合适的插入位置将其插入，并保证已排序区间数据一直有序。
 * 重复这个过程，直到未排序区间中元素为空，算法结束。
 * <p>
 * note
 * 借用插入算法思想：往一个有序集合中插入一个值保证还是有序的做法。
 */
public class InsertionSort {

    public static void main(String[] args) {

        int arr[] = new int[]{3, 9, 10, 11, 7, 8, 55, 1};

        insertionSort(arr);
        Arrays.stream(arr).forEach(a -> {
            System.out.println(a);
        });


    }

    private static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (arr[j] > temp) {
                    arr[j+1] = arr[j];
                }else {
                    // arr[j+1] = temp;// 因为在break的位置后面插入所以+1
                    break;
                }
            }
            arr[j+1] = temp;// 为什么要放在这里赋值 ？ l35不行吗
        }
    }
}
