package com.lvyongwenhouzi.arithmetic;

/**
 * <b>description</b>
 *
 * @author 11114396 lvyongwen
 * @date 2021-03-12 10:16
 * @since 1.0
 */
public class TreeNode {

    int val = 0;
    TreeNode left = null;
    TreeNode right = null;


    public TreeNode(int val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "val=" + val +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}
