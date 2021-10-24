package com.lvyongwenhouzi.server.structure;

import java.util.Arrays;

/**
 * 冒泡排序只会操作相邻的两个数据。每次冒泡操作都会对相邻的两个元素进行比较，看是否满足大小关系要求。
 * 如果不满足就让它俩互换。一次冒泡会让至少一个元素移动到它应该在的位置，重复 n 次，就完成了 n 个数据的排序工作。
 */
public class BubbleSort {

    public static void main(String[] args) {

        int arr[] = new int[]{3, 9, 10, 11, 7, 8, 55, 1};

        bubbleSort(arr);
        Arrays.stream(arr).forEach(a -> {
            System.out.println(a);
        });
    }

    private static void bubbleSort(int[] arr) {

        boolean flag = false;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    flag = true;
                }
            }
            if (flag) // 优化点：没有交换元素则认为已经有序。
                break;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
