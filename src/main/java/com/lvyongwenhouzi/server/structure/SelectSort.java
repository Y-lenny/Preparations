package com.lvyongwenhouzi.server.structure;

import java.util.Arrays;

/**
 * 选择排序算法的实现思路有点类似插入排序，也分已排序区间和未排序区间。但是选择排序每次会从未排序区间中找到最小的元素，将其放到已排序区间的末尾。
 */
public class SelectSort {

    public static void main(String[] args) {
        int arr[] = new int[]{3, 9, 10, 11, 7, 8, 55, 1};

        selectSort(arr);
        Arrays.stream(arr).forEach(a -> {
            System.out.println(a);
        });
    }

    private static void selectSort(int[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {
            int tempIndex = i;
            for (int j = i; j < arr.length; j++) {
                if (arr[j] < arr[tempIndex]) {
                    tempIndex = j;
                }
            }
            if (tempIndex != i) // 最小值是本身就进行交换
                swap(arr, i, tempIndex);
        }
    }

    private static void swap(int arr[], int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
