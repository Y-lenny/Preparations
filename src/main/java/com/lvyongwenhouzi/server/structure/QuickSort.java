package com.lvyongwenhouzi.server.structure;

import java.util.Arrays;

/**
 * 快排的思想是这样的：如果要排序数组中下标从 p 到 r 之间的一组数据，我们选择 p 到 r 之间的任意一个数据作为 pivot（分区点）。
 * 我们遍历 p 到 r 之间的数据，
 * 将小于 pivot 的放到左边，将大于 pivot 的放到右边，将 pivot 放到中间。
 * 经过这一步骤之后，数组 p 到 r 之间的数据就被分成了三个部分，前面 p 到 q-1 之间都是小于 pivot 的，中间是 pivot，后面的 q+1 到 r 之间是大于 pivot 的。
 * <p>
 * 递推公式：
 * quick_sort(p…r) = quick_sort(p…q-1) + quick_sort(q+1, r)
 * 终止条件：
 * p >= r
 */
public class QuickSort {

    public static void main(String[] args) {


        int arr[] = new int[]{3, 9, 10, 11, 7, 8, 55, 1};

        // 排序
        quickSort(arr, 0, 7);
        Arrays.stream(arr).forEach(a -> {
            System.out.println(a);
        });


    }

    public static void quickSort(int[] arr, int p, int r) {

        if (p >= r) {
            return;
        }
        // 获取数组的分区位置
        int q = partition(arr, p, r);
        quickSort(arr, p, q - 1);
        quickSort(arr, q + 1, r);
    }

    /**
     * 按照指定的位置进行分区（实现方式有点类似插入排序）
     * @param arr
     * @param p
     * @param r
     * @return
     */
    private static int partition(int[] arr, int p, int r) {

        int q = r; // pivot默认为[p,r]之间的最后一个元素（非常重要）
        int i = p; // 通过i把数组分为[p,i]已处理区、[i+1,r]为未处理区；然后遍历未处理区元素进行比较交换至处理区。

        for (int j = p; j < r-1; j++) {
            if (arr[j] <= arr[q]) {
                swap(arr, i, j);
                i ++;
            }
        }
        // 重置分区位置
        swap(arr,i,q);
        return i;
    }

    private static void swap(int arr[], int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


}
