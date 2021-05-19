//给你两个图像 img1 和 img2 ，两个图像的大小都是 n x n ，用大小相同的二维正方形矩阵表示。（并且为二进制矩阵，只包含若干 0 和若干 1 ）
//
//
// 转换其中一个图像，向左，右，上，或下滑动任何数量的单位，并把它放在另一个图像的上面。之后，该转换的 重叠 是指两个图像都具有 1 的位置的数目。
//
//
//
// （请注意，转换 不包括 向任何方向旋转。）
//
// 最大可能的重叠是多少？
//
//
//
// 示例 1：
//
//
//输入：img1 = [[1,1,0],[0,1,0],[0,1,0]], img2 = [[0,0,0],[0,1,1],[0,0,1]]
//输出：3
//解释：将 img1 向右移动 1 个单位，再向下移动 1 个单位。
//
//两个图像都具有 1 的位置的数目是 3（用红色标识）。
//
//
//
// 示例 2：
//
//
//输入：img1 = [[1]], img2 = [[1]]
//输出：1
//
//
// 示例 3：
//
//
//输入：img1 = [[0]], img2 = [[0]]
//输出：0
//
//
//
//
// 提示：
//
//
// n == img1.length
// n == img1[i].length
// n == img2.length
// n == img2[i].length
// 1 <= n <= 30
// img1[i][j] 为 0 或 1
// img2[i][j] 为 0 或 1
//
//
//
// Related Topics 数组
// 👍 66 👎 0


package com.lvyongwenhouzi.leetcode.editor.cn;
public class ImageOverlap{
  public static void main(String[] args) {
       Solution solution = new ImageOverlap().new Solution();
  }
  //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int largestOverlap(int[][] img1, int[][] img2) {
        return 0;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}
