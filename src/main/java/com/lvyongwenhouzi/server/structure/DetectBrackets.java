package com.lvyongwenhouzi.server.structure;

import com.lvyongwenhouzi.server.leetcode.editor.cn.ValidParentheses;
import lombok.Data;

/**
 * 检查表达式中的括号是否匹配 ?
 * [] {} -- 匹配
 * [}    -- 不匹配
 * 1、定义匹配规则（如上）
 * 2、判断是入栈还是出栈，出栈的时候判断元素是否匹配
 *
 * 技巧：
 * 类似这种匹配对称关系、先进后出、先出后进的这种场景都应该考虑栈实现
 *
 * leecode：{@link ValidParentheses}
 */
public class DetectBrackets {

    public static void main(String[] args) {

        String str1 = "([11111]{22222})";

        String str2 = "([11111})22222]{";

        char[] charArray = str2.toCharArray();

        Stack stack1 = new Stack(charArray.length);

        for (int i = 0; i < charArray.length; i++) {

            if ("([{".contains(charArray[i] + "")) {
                // 入栈
                stack1.push(charArray[i]);
            } else if (")]}".contains(charArray[i] + "")) {
                // 出栈
                char pop = stack1.pop();
                if (charArray[i] == ')' && pop == '(') {
                    continue;
                } else if (charArray[i] == ']' && pop == '[') {
                    continue;
                } else if (charArray[i] == '}' && pop == '{') {
                    continue;
                } else {
                    System.out.println(">>>>>>>>>>" + charArray[i] + "!=" + pop);
                }
            } else {
                System.out.println(">>>>>>>>>>>" + charArray[i]);
            }
        }
    }


    /**
     * 栈的数据结构实现
     */
    @Data
    public static class Stack {

        /**
         * 数组
         */
        private char eleArr[];

        /**
         * 大小
         */
        private Integer size;

        /**
         * 长度
         */
        private Integer length;

        public Stack(Integer length) {
            this.length = length;
            this.eleArr = new char[10];
            this.size = 0;
        }

        /**
         * 入栈
         *
         * @param data
         * @return
         */
        public boolean push(char data) {

            if (size == length) {
                return false;
            }

            eleArr[size++] = data;
            return true;
        }

        /**
         * 出栈
         *
         * @return
         */
        public char pop() {

            if (size == 0) {
                return 0;
            }

            return eleArr[--size];
        }
    }


}
