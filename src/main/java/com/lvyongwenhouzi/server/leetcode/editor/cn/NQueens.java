//n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
//
// 给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。
//
//
//
// 每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
//
//
//
// 示例 1：
//
//
//输入：n = 4
//输出：[[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
//解释：如上图所示，4 皇后问题存在两个不同的解法。
//
//
// 示例 2：
//
//
//输入：n = 1
//输出：[["Q"]]
//
//
//
//
// 提示：
//
//
// 1 <= n <= 9
// 皇后彼此不能相互攻击，也就是说：任何两个皇后都不能处于同一条横行、纵行或斜线上。
//
//
//
// Related Topics 数组 回溯
// 👍 1094 👎 0


package com.lvyongwenhouzi.server.leetcode.editor.cn;

import java.util.*;

public class NQueens {
    public static void main(String[] args) {
        Solution solution = new NQueens().new Solution();
        // System.out.println(solution.solveNQueens(5));;
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public List<List<String>> solveNQueens(int n) {
            // 保存皇后可以排布的集合列表
            List<List<String>> queens = new ArrayList<>();
            // 创建冲突集合，提高判断是否存在攻击可能
            int[] rows = new int[n];
            Arrays.fill(rows, -1);
            Set<Integer> columns = new HashSet<>();
            Set<Integer> slash1 = new HashSet<>();
            Set<Integer> slash2 = new HashSet<>();
            backtracking(0, n, queens, columns, slash1, slash2, rows);
            return queens;
        }

        /**
         * 回溯方法
         * @param row   行
         * @param n 皇后矩阵
         * @param queens    皇后组合集合
         * @param columns   列互斥
         * @param slash1    左下斜线互斥
         * @param slash2    右下斜线互斥
         * @param rows      皇后位置
         */
        private void backtracking(int row, int n, List<List<String>> queens, Set<Integer> columns, Set<Integer> slash1, Set<Integer> slash2, int[] rows) {
            if (row == n) {
                // 表示已经回溯到了可以满足互不冲突的组合
                List<String> board = transChar(row, rows);
                queens.add(board);
            } else {
                for (int i = 0; i < n; i++) {

                    // 列互相冲突
                    if (columns.contains(i)) {
                        continue;
                    }
                    // 左下斜线互相冲突
                    int s1 = row - i;
                    if (slash1.contains(s1)) {
                        continue;
                    }
                    // 右下斜线互相冲突
                    int s2 = row + i;
                    if (slash2.contains(s2)) {
                        continue;
                    }

                    // 保存行皇后标识，并把斜线、列值保存
                    rows[row] = i;
                    columns.add(i);
                    slash1.add(s1);
                    slash2.add(s2);

                    // 继续下一行皇后排列
                    backtracking(row + 1, n, queens, columns, slash1, slash2, rows);

                    // 回溯上一步
                    rows[row] = -1;
                    columns.remove(i);
                    slash1.remove(s1);
                    slash2.remove(s2);
                }
            }
        }

        /**
         * 字符转字符串
         * @param n
         * @param rows 皇后位置
         * @return
         */
        private List<String> transChar(int n, int[] rows) {
            List<String> queen = new ArrayList<>();
            char[] c = new char[n];
            for (int i = 0; i < n; i++) {
                Arrays.fill(c, '.');
                c[rows[i]] = 'Q';
                queen.add(String.valueOf(c));
            }
            return queen;
        }
    }

//leetcode submit region end(Prohibit modification and deletion)

}
