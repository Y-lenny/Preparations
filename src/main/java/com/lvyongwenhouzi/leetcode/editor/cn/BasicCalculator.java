//给你一个字符串表达式 s ，请你实现一个基本计算器来计算并返回它的值。
//
//
//
// 示例 1：
//
//
//输入：s = "1 + 1"
//输出：2
//
//
// 示例 2：
//
//
//输入：s = " 2-1 + 2 "
//输出：3
//
//
// 示例 3：
//
//
//输入：s = "(1+(4+5+2)-3)+(6+8)"
//输出：23
//
//
//
//
// 提示：
//
//
// 1 <= s.length <= 3 * 105
// s 由数字、'+'、'-'、'('、')'、和 ' ' 组成
// s 表示一个有效的表达式
//
// Related Topics 栈 递归 数学 字符串
// 👍 609 👎 0


package com.lvyongwenhouzi.leetcode.editor.cn;

import java.util.Deque;
import java.util.LinkedList;

public class BasicCalculator {
    public static void main(String[] args) {
        Solution solution = new BasicCalculator().new Solution();
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int calculate(String s) {

            Deque<Integer> deque = new LinkedList();

            int ops = 1;
            deque.push(ops);
            int i = 0;
            int sum = 0;

            while (i < s.length()) {
                if (s.charAt(i) == ' ') {
                    i++;
                } else if (s.charAt(i) == '+') {
                    ops = deque.peek();
                    i++;
                } else if (s.charAt(i) == '-') {
                    ops = -deque.peek();
                    i++;
                } else if (s.charAt(i) == '(') {
                    deque.push(ops);
                    i++;
                } else if (s.charAt(i) == ')') {
                    deque.pop();
                    i++;
                } else {
                    // 数字
                    int num = 0;
                    while (i < s.length() && Character.isDigit(s.charAt(i))) {
                        num = num * 10 + Character.getNumericValue(s.charAt(i));
                        i++;
                    }
                    sum += ops * num;
                }
            }
            return sum;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
