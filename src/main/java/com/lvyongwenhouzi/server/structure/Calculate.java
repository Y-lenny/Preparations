package com.lvyongwenhouzi.server.structure;


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
 * 5、计算值为多位需要判断连续后一位是否是相同类型：10
 * 6、计算值为负数计算时需要判为空使用默认值0 -1 = 0-1
 * 如果比运算符栈顶元素的优先级高，就将当前运算符压入栈；如果比运算符栈顶元素的优先级低或者相同，从运算符栈中取栈顶运算符，
 * 从操作数栈的栈顶取 2 个操作数，然后进行计算，再把计算完的结果压入操作数栈，继续比较。
 */
public class Calculate {


    public static void main(String[] args) {

        Stack<Character> symbol = new Stack();
        Stack<Integer> number = new Stack();

        char chars[] = "1+2+3+4(3+9+999)-1+(-1)".toCharArray();

        for (int i = 0; i < chars.length; i++) {

            char c = chars[i];
            if ('(' == c) {
                symbol.push(c);
            } else if (')' == c) {
                if (!symbol.isEmpty()) {
                    int search = symbol.lastIndexOf('(');
                    for (int j = symbol.size() - 1; j > search; j--) {
                        number.push(calc(number.isEmpty() ? 0 : number.pop(), symbol.pop(), number.isEmpty() ? 0 : number.pop()));
                    }
                    symbol.pop();
                }
            } else if ("+-*/".contains(c + "")) {
                if (!symbol.isEmpty()) {
                    int search = symbol.lastIndexOf('(');
                    for (int j = symbol.size() - 1; j > search; j--) {
                        if (compare(c, symbol.peek()) <= 0) {
                            number.push(calc(number.isEmpty() ? 0 : number.pop(), symbol.pop(), number.isEmpty() ? 0 : number.pop()));
                        }
                    }
                }
                symbol.push(c);
            } else if (' ' == c) {
                continue;
            } else if ((c >= '0' && c <= '9')) {
                number.push(Character.getNumericValue(c));
                while (++i < chars.length) {
                    if (chars[i] >= '0' && chars[i] <= '9') {
                        //计算a的b次方
                        number.push(Character.getNumericValue(chars[i]) + 10 * (number.pop()));
                    } else {
                        --i;
                        break;
                    }
                }
            }
        }

        // 弹出符号栈中操作
        while (!symbol.isEmpty()) {
            number.push(calc(number.isEmpty() ? 0 : number.pop(), symbol.pop(), number.isEmpty() ? 0 : number.pop()));
        }

        System.out.println(">>>>>>>>>>>>>>>>>>" + number.pop());

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

    public static int calc(int second, char op, int first) {
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
