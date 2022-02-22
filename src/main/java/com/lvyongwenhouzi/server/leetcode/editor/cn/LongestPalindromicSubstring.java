//给你一个字符串 s，找到 s 中最长的回文子串。
//
//
//
// 示例 1：
//
//
//输入：s = "babad"
//输出："bab"
//解释："aba" 同样是符合题意的答案。
//
//
// 示例 2：
//
//
//输入：s = "cbbd"
//输出："bb"
//
//
//
//
// 提示：
//
//
// 1 <= s.length <= 1000
// s 仅由数字和英文字母组成
//
// Related Topics 字符串 动态规划 👍 4727 👎 0


package com.lvyongwenhouzi.server.leetcode.editor.cn;

public class LongestPalindromicSubstring {
    public static void main(String[] args) {
        Solution solution = new LongestPalindromicSubstring().new Solution();
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public String longestPalindrome(String s) {
            int len = s.length();
            // 当字符串<2代表无需要比较前后字符串是否相同以及子串是否是回文串
            if (len < 2) {
                return s;
            }

            // 使用二维数组表示是否是回文串
            boolean[][] index = new boolean[len][len];
            // 初始化单个字符串为回文串
            for (int i = 0; i < len; i++) {
                index[i][i] = true;
            }

            // 通过状态转移表法进行子传的状态转移
            int maxLen = 1;
            int begin = 0;
            // 子串长度转移
            for (int l = 2; l <= len; l++) {
                for (int i = 0; i < len; i++) {
                    // 根据 l = j - i + 1 可以推倒j的值
                    int j = l + i - 1;

                    // 如果右边边界越界可直接返回
                    if (j >= len) {
                        break;
                    }

                    if (s.charAt(i) != s.charAt(j)) {
                        index[i][j] = false;
                    } else {
                        // 边界字符相等就需要判断子串是否是回文串和下标是否是j-i+1 < 3
                        if (j - i < 3) {
                            index[i][j] = true;
                        } else {
                            index[i][j] = index[i + 1][j - 1];
                        }
                    }

                    // index[i][j] = true 并且大于最大子串就可以记录下来
                    if (index[i][j] && j - i + 1 > maxLen) {
                        maxLen = j - i + 1;
                        begin = i;
                    }
                }
            }

            // 返回
            return s.substring(begin, begin + maxLen);
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
