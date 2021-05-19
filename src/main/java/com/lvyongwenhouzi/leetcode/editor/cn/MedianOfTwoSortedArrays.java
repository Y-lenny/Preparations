//给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
//
//
//
// 示例 1：
//
//
//输入：nums1 = [1,3], nums2 = [2]
//输出：2.00000
//解释：合并数组 = [1,2,3] ，中位数 2
//
//
// 示例 2：
//
//
//输入：nums1 = [1,2], nums2 = [3,4]
//输出：2.50000
//解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
//
//
// 示例 3：
//
//
//输入：nums1 = [0,0], nums2 = [0,0]
//输出：0.00000
//
//
// 示例 4：
//
//
//输入：nums1 = [], nums2 = [1]
//输出：1.00000
//
//
// 示例 5：
//
//
//输入：nums1 = [2], nums2 = []
//输出：2.00000
//
//
//
//
// 提示：
//
//
// nums1.length == m
// nums2.length == n
// 0 <= m <= 1000
// 0 <= n <= 1000
// 1 <= m + n <= 2000
// -106 <= nums1[i], nums2[i] <= 106
//
//
//
//
// 进阶：你能设计一个时间复杂度为 O(log (m+n)) 的算法解决此问题吗？
// Related Topics 数组 二分查找 分治算法
// 👍 4094 👎 0


package com.lvyongwenhouzi.leetcode.editor.cn;

public class MedianOfTwoSortedArrays {
    public static void main(String[] args) {
        Solution solution = new MedianOfTwoSortedArrays().new Solution();

        int[] nums1 = new int[]{2,2,4,4};
        int[] nums2 = new int[]{2,2,4,4};
        System.out.println(solution.findMedianSortedArrays(nums1, nums2));
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {

            int[] nums3 = new int[nums1.length + nums2.length];
            int nums1Index = 0;
            int nums2Index = 0;

            for (int i = 0; i < nums1.length + nums2.length; i++) {

                if (nums1Index < nums1.length && nums2Index < nums2.length) {
                    if (nums1[nums1Index] < nums2[nums2Index]) {
                        nums3[i] = nums1[nums1Index];
                        nums1Index++;
                    } else if (nums1[nums1Index] > nums2[nums2Index]) {
                        nums3[i] = nums2[nums2Index];
                        nums2Index++;
                    } else {
                        nums3[i] = nums2[nums2Index];
                        nums2Index++;
                        nums3[i+1] = nums1[nums1Index];
                        nums1Index++;
                        i++;
                    }
                } else if (nums1Index < nums1.length && !(nums2Index < nums2.length)) {
                    nums3[i] = nums1[nums1Index];
                    nums1Index++;
                } else if (!(nums1Index < nums1.length) && nums2Index < nums2.length) {
                    nums3[i] = nums2[nums2Index];
                    nums2Index++;
                }
            }

            if (nums3.length % 2 != 0) {
                return nums3[nums3.length/2];
            } else {
                return (nums3[nums3.length / 2 -1 ] + nums3[nums3.length / 2]) / 2.0;
            }
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
