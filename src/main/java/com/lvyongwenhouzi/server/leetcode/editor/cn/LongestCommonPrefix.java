//编写一个函数来查找字符串数组中的最长公共前缀。
//
// 如果不存在公共前缀，返回空字符串 ""。
//
//
//
// 示例 1：
//
//
//输入：strs = ["flower","flow","flight"]
//输出："fl"
//
//
// 示例 2：
//
//
//输入：strs = ["dog","racecar","car"]
//输出：""
//解释：输入不存在公共前缀。
//
//
//
// 提示：
//
//
// 1 <= strs.length <= 200
// 0 <= strs[i].length <= 200
// strs[i] 仅由小写英文字母组成
//
// Related Topics 字符串 👍 2041 👎 0


package com.lvyongwenhouzi.server.leetcode.editor.cn;

public class LongestCommonPrefix {
    public static void main(String[] args) {
        Solution solution = new LongestCommonPrefix().new Solution();
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public String longestCommonPrefix(String[] strs) {
            if (strs == null || strs.length == 0) {
                return "";
            }

            // 第一数组作为最长前缀初始值
            String prefix = strs[0];
            for (int i = 1; i < strs.length; i++) {
                prefix = compare(prefix, strs[i]);
                if (prefix == "") {
                    break;
                }
            }
            return prefix;
        }

        private String compare(String prefix, String str) {

            int longer = Math.min(prefix.length(), str.length());
            int index = 0;
            for (int i = 0; i < longer; i++) {
                if (prefix.charAt(i) == str.charAt(i)) {
                    index++;
                } else {
                    break;
                }
            }
            return prefix.substring(0, index);
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
