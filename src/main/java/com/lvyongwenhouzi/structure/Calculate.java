package com.lvyongwenhouzi.structure;

import com.sun.tools.javac.util.Assert;

import java.util.Stack;

/**
 * 栈如何在表达式求值中的应用 ?
 * 例： 4 * ( 1 + ( 4 * 5 + (5 * 7) - 4)) = 208
 * 1、
 * <p>
 * 技巧：
 * 1、'(' ')'属于标记开始、结束
 * 2、* / 优先级 高于 + -
 * 3、使用两个栈进行运算：一个用于存储符号、一个用于存储数字
 * 4、'('直接入入栈、')'计算栈顶直到'('为止;
 * 如果比运算符栈顶元素的优先级高，就将当前运算符压入栈；如果比运算符栈顶元素的优先级低或者相同，从运算符栈中取栈顶运算符，
 * 从操作数栈的栈顶取 2 个操作数，然后进行计算，再把计算完的结果压入操作数栈，继续比较。
 */
public class Calculate {


    public static void main(String[] args) {

        String str = "4*(1+(4*5+(5*7)-4))";

        Stack<Character> symbol = new Stack();

        Stack<Integer> number = new Stack();

        char chars[] = str.toCharArray();

        for (int i = 0; i < chars.length; i++) {

            char c = chars[i];
            if ('(' == c) {
                symbol.push(c);
            } else if (')' == c) {
                if (!symbol.isEmpty()) {
                    int search = symbol.search('(');
                    for (int j = symbol.size(); j > search; j--) {
                        number.push(calc(number.pop(), symbol.pop(), number.pop()));
                    }
                    Assert.check(symbol.pop() == '(');
                }
            } else if ("+-*/".contains(c + "")) {
                if (!symbol.isEmpty()){
                    if (compare(c, symbol.peek()) <= 0) {
                        int search = symbol.search('(');
                        for (int j = symbol.size(); j > search; j--) {
                            number.push(calc(number.pop(), symbol.pop(), number.pop()));
                        }
                    }
                }
                symbol.push(c);
            } else if (' ' == c) {
                continue;
            } else if ((c >= '0' && c <= '9')) {
                number.push(Integer.valueOf(c));
            }
        }
    }

    public static int compare(char op1, char op2) {
        if ("+-".contains(op1 + "") && "*/".contains(op2 + "")) {
            return -1;
        } else if ("*/".contains(op1 + "") && "+-".contains(op2 + "")) {
            return 1;
        } else {
            return 0;
        }
    }

    public static int calc(int first, char op, int second) {
        if (op == '*') {
            return first * second;
        } else if (op == '/') {
            return first / second;
        } else if (op == '+') {
            return first + second;
        } else if (op == '-') {
            return first - second;
        }
        return 0;
    }


}
