//老师想给孩子们分发糖果，有 N 个孩子站成了一条直线，老师会根据每个孩子的表现，预先给他们评分。
//
// 你需要按照以下要求，帮助老师给这些孩子分发糖果：
//
//
// 每个孩子至少分配到 1 个糖果。
// 评分更高的孩子必须比他两侧的邻位孩子获得更多的糖果。
//
//
// 那么这样下来，老师至少需要准备多少颗糖果呢？
//
//
//
// 示例 1：
//
//
//输入：[1,0,2]
//输出：5
//解释：你可以分别给这三个孩子分发 2、1、2 颗糖果。
//
//
// 示例 2：
//
//
//输入：[1,2,2]
//输出：4
//解释：你可以分别给这三个孩子分发 1、2、1 颗糖果。
//     第三个孩子只得到 1 颗糖果，这已满足上述两个条件。
// Related Topics 贪心 数组
// 👍 680 👎 0


package com.lvyongwenhouzi.server.leetcode.editor.cn;

public class Candy {
    public static void main(String[] args) {
        Solution solution = new Candy().new Solution();
        System.out.println(solution.candy(new int[]{1,2,3}));
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int candy(int[] ratings) {

            if (ratings == null || ratings.length == 0) {
                return 0;
            }

            if (ratings.length == 1) {
                return 1;
            }


            // 总计
            int ratingsArr[] = new int[ratings.length];

            for (int i = 0; i < ratings.length; i++) {
                // 1、每次循环找到最小值进行分配
                int minIndex = minIndex(ratings, ratingsArr);
                // 2、判断左右的是否存在元素并且值大小

                if (minIndex == 0) {
                    // 没有左侧对比
                    if (ratings[minIndex] > ratings[minIndex + 1]) {
                        ratingsArr[minIndex] = ratingsArr[minIndex + 1] + 1;
                    } else {
                        ratingsArr[minIndex] = 1;
                    }
                } else if (minIndex == ratings.length - 1) {
                    // 没有右侧对比
                    if (ratings[minIndex] > ratings[minIndex - 1]) {
                        ratingsArr[minIndex] = ratingsArr[minIndex - 1] + 1;
                    } else {
                        ratingsArr[minIndex] = 1;
                    }
                } else {
                    // 两侧都需要对比
                    if (ratings[minIndex] > ratings[minIndex - 1] && ratings[minIndex] > ratings[minIndex + 1]) {
                        ratingsArr[minIndex] = Math.max(ratingsArr[minIndex - 1], ratingsArr[minIndex + 1]) + 1;
                    } else if (ratings[minIndex] > ratings[minIndex - 1] && ratings[minIndex] < ratings[minIndex + 1]) {
                        ratingsArr[minIndex] = ratingsArr[minIndex - 1] + 1;
                    } else if ((ratings[minIndex] > ratings[minIndex - 1] && ratings[minIndex] > ratings[minIndex + 1])) {
                        ratingsArr[minIndex] = ratingsArr[minIndex + 1] + 1;
                    } else {
                        ratingsArr[minIndex] = 1;
                    }
                }
            }

            // 3、计数
            int total = 0;
            for (int i = 0; i < ratingsArr.length; i++) {
                total += ratingsArr[i];
            }
            return total;
        }

        /**
         * 取最小值
         *
         * @param ratings
         * @param ratingsArr
         * @return
         */
        public int minIndex(int[] ratings, int ratingsArr[]) {

            int minIndex = 0;
            for (int i = 0; i < ratings.length; i++) {
                if (ratingsArr[i] != 0){
                    continue;
                }


                if (ratings[minIndex] >= ratings[i]) {
                    minIndex = i;
                }
            }
            return minIndex;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
