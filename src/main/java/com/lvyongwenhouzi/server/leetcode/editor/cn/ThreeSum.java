//给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重
//复的三元组。
//
// 注意：答案中不可以包含重复的三元组。
//
//
//
// 示例 1：
//
//
//输入：nums = [-1,0,1,2,-1,-4]
//输出：[[-1,-1,2],[-1,0,1]]
//
//
// 示例 2：
//
//
//输入：nums = []
//输出：[]
//
//
// 示例 3：
//
//
//输入：nums = [0]
//输出：[]
//
//
//
//
// 提示：
//
//
// 0 <= nums.length <= 3000
// -10⁵ <= nums[i] <= 10⁵
//
// Related Topics 数组 双指针 排序 👍 4343 👎 0


package com.lvyongwenhouzi.server.leetcode.editor.cn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSum {
    public static void main(String[] args) {
        Solution solution = new ThreeSum().new Solution();
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public List<List<Integer>> threeSum(int[] nums) {

            // 首先进行排序
            Arrays.sort(nums);
            int n = nums.length;
            List<List<Integer>> combination = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                // 需要跳过和前一次元素相同位置
                if (i > 0 && nums[i] == nums[i - 1]) {
                    continue;
                }
                // 双指针
                int k = n - 1;
                int target = -nums[i];
                for (int j = i + 1; j < n; j++) {
                    // 需要跳过和前一次元素相同位置
                    if (j > i + 1 && nums[j] == nums[j - 1]) {
                        continue;
                    }

                    // k向左移动到a+b+c > 0的位置
                    while (j < k && nums[j] + nums[k] > target) {
                        --k;
                    }

                    if (j == k) {
                        break;
                    }
                    if (nums[j] + nums[k] == target) {
                        List<Integer> sum = new ArrayList<>();
                        sum.add(nums[i]);
                        sum.add(nums[j]);
                        sum.add(nums[k]);
                        combination.add(sum);
                    }
                }
            }
            return combination;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
