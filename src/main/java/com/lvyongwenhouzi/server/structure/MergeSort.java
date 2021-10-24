package com.lvyongwenhouzi.server.structure;


import java.util.Arrays;

/**
 * 归并排序的核心思想还是蛮简单的。如果要排序一个数组，我们先把数组从中间分成前后两部分，然后对前后两部分分别排序，再将排好序的两部分合并在一起，这样整个数组就都有序了。
 * <p>
 * 递推公式：
 * merge_sort(p…r) = merge(merge_sort(p…q), merge_sort(q+1…r))
 * 终止条件：
 * p >= r 不用再继续分解
 */
public class MergeSort {

    public static void main(String[] args) {
        int arr[] = new int[]{3, 9, 10, 11, 7, 8, 55, 1};
        mergeSort(arr, 0, 7);
        Arrays.stream(arr).forEach(a -> {
            System.out.println(a);
        });
    }

    /**
     * 递归函数
     * @param arr 数组
     * @param p 开始
     * @param r 结束
     */
    public static void mergeSort(int arr[], int p, int r) {
        // 终止条件
        if (p >= r) {
            return;
        }
        // 取中间位置
        int q = (p + r) / 2;
        // 一分为二（拆分）
        mergeSort(arr, p, q);
        mergeSort(arr, q + 1, r);
        // 分二为一（合并）
        merge(arr, p, q, r);
    }

    /**
     * 两个有序数组进行合并
     * @param arr
     * @param p 开始
     * @param q 中间
     * @param r 结束
     */
    private static void merge(int arr[], int p, int q, int r) {

        int i = p;
        int j = q + 1;
        int k = 0;
        int temp[] = new int[r - p + 1];
        // 合并可比较区间
        while (i <= q && j <= r) {
            if (arr[i] < arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        // 合并左半剩余部分
        while (i <= q) {
            temp[k++] = arr[i++];
        }
        // 合并右半剩余部分
        while (j <= r) {
            temp[k++] = arr[j++];
        }
        // 覆盖p,q之间的值
        for (int l = 0; l < temp.length; l++) {
            arr[p + l] = temp[l];
        }


    }
}
