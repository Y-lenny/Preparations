package com.lvyongwenhouzi.server.structure;

/**
 * 二分查找：有序数组
 * 定义
 * 二分查找针对的是一个有序的数据集合，查找思想有点类似分治思想。每次都通过跟区间的中间元素对比，将待查找的区间缩小为之前的一半，直到找到要查找的元素，或者区间被缩小为 0。
 *
 * 局限性
 * 二分查找依赖的是顺序表结构，简单点说就是数组。
 * 二分查找针对的是有序数据(二分查找只能用在插入、删除操作不频繁，一次排序多次查找的场景中二叉树是好的解决方案)
 * 数据量太小不适合二分查找（如果数据之间的比较操作非常耗时，不管数据量大小，我都推荐使用二分查找）
 * 数据量太大也不适合二分查找（存储在数组中，数组空间是连续的）
 */
public class BinarySearch {

    public static void main(String[] args) {

        int arr[] = new int[]{1, 2, 3, 4, 56, 77, 88, 99, 100};
        int idx = binarySearch(arr, 100, 0, 8);
        System.out.println(idx);
    }

    private static int binarySearch(int[] arr, int el, int start, int end) {

        if (start > end) { // 递归终止条件
            return -1;
        }
        int middleIndex = (end + start) / 2; // 取中间位置也可以写成：nt mid = low + ((high - low) >> 1); 使用位操作
        if (arr[middleIndex] == el) {
            return middleIndex;
        }
        if (arr[middleIndex] > el) {
            return binarySearch(arr, el, start, middleIndex - 1); // 重新选定区域进行比较
        }

        if (arr[middleIndex] < el) {
            return binarySearch(arr, el, middleIndex + 1, end);// 重新选定区域进行比较
        }
        return -1;
    }


}
