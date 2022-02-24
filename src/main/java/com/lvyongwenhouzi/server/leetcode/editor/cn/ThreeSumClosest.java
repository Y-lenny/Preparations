//给你一个长度为 n 的整数数组 nums 和 一个目标值 target。请你从 nums 中选出三个整数，使它们的和与 target 最接近。
//
// 返回这三个数的和。
//
// 假定每组输入只存在恰好一个解。
//
//
//
// 示例 1：
//
//
//输入：nums = [-1,2,1,-4], target = 1
//输出：2
//解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2) 。
//
//
// 示例 2：
//
//
//输入：nums = [0,0,0], target = 1
//输出：0
//
//
//
//
// 提示：
//
//
// 3 <= nums.length <= 1000
// -1000 <= nums[i] <= 1000
// -10⁴ <= target <= 10⁴
//
// Related Topics 数组 双指针 排序 👍 1048 👎 0


package com.lvyongwenhouzi.server.leetcode.editor.cn;

import java.util.Arrays;

public class ThreeSumClosest {
    public static void main(String[] args) {
        Solution solution = new ThreeSumClosest().new Solution();
        solution.threeSumClosest(new int[]{-1, 2, 1, -4}, 1);
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int threeSumClosest(int[] nums, int target) {
            // 排序
            Arrays.sort(nums);

            int len = nums.length;
            int best = 10000;
            for (int i = 0; i < len; i++) {

                // 双指针
                int j = i + 1;
                int k = len - 1;

                while (j < k) {
                    int sum = nums[i] + nums[j] + nums[k];
                    if (sum == target) {
                        return sum;
                    }
                    // 更新最接近值
                    if (Math.abs(sum - target) < Math.abs(best - target)) {
                        best = sum;
                    }
                    // 更新右指针
                    if (sum > target) {
                        do {
                            k--;
                        } while (j < k && nums[k] == nums[k + 1]);
                    }

                    // 更新左指针
                    if (sum < target) {
                        do {
                            j++;
                        } while (j < k && nums[j] == nums[j - 1]);
                    }
                }
            }
            return best;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
