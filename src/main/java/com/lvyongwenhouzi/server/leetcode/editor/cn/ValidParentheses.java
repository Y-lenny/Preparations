//给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
//
// 有效字符串需满足：
//
//
// 左括号必须用相同类型的右括号闭合。
// 左括号必须以正确的顺序闭合。
//
//
//
//
// 示例 1：
//
//
//输入：s = "()"
//输出：true
//
//
// 示例 2：
//
//
//输入：s = "()[]{}"
//输出：true
//
//
// 示例 3：
//
//
//输入：s = "(]"
//输出：false
//
//
// 示例 4：
//
//
//输入：s = "([)]"
//输出：false
//
//
// 示例 5：
//
//
//输入：s = "{[]}"
//输出：true
//
//
//
// 提示：
//
//
// 1 <= s.length <= 104
// s 仅由括号 '()[]{}' 组成
//
// Related Topics 栈 字符串
// 👍 2550 👎 0


package com.lvyongwenhouzi.server.leetcode.editor.cn;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ValidParentheses {
    public static void main(String[] args) {
        Solution solution = new ValidParentheses().new Solution();
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public boolean isValid(String s) {

            int n = s.length();
            if (n % 2 == 1) {
                return false;
            }

            // 匹配规则
            Map<Character, Character> rule = new HashMap() {{
                put(')', '(');
                put('}', '{');
                put(']', '[');
            }};

            char[] characters = s.toCharArray();
            Deque deque = new LinkedList();
            for (int i = 0; i < characters.length; i++) {

                if (rule.containsKey(characters[i])) {
                    // 不存在匹配字符 / 字符不匹配
                    if (deque.isEmpty() || deque.peek() != rule.get(characters[i])) {
                        return false;
                    }
                    deque.pop();
                } else {
                    deque.push(characters[i]);
                }
            }

            // 是否为空
            return deque.isEmpty();
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
