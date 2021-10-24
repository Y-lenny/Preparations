package com.lvyongwenhouzi.server.structure;

import java.util.LinkedList;
import java.util.List;

/**
 * 二叉树的遍历
 * 前序遍历是指，对于树中的任意节点来说，先打印这个节点，然后再打印它的左子树，最后打印它的右子树。
 * 中序遍历是指，对于树中的任意节点来说，先打印它的左子树，然后再打印它本身，最后打印它的右子树。
 * 后序遍历是指，对于树中的任意节点来说，先打印它的左子树，然后再打印它的右子树，最后打印这个节点本身。
 */
public class BTTraversal {

    public static void main(String[] args) {

        TreeNode[] treeNodes = new TreeNode[10];
        // 初始化值
        for (int i = 1; i <= treeNodes.length; i++) {
            treeNodes[i - 1] = new TreeNode(i);
        }
        // 构建二叉树关联关系(骚气构造树)
        for (int i = 1; i <= treeNodes.length; i++) {
            if (2 * i + 1 < 11) {
                treeNodes[i - 1].leftNode = treeNodes[2 * i - 1];
            }
            if (2 * i + 2 < 11) {
                treeNodes[i - 1].rightNode = treeNodes[2 * i];
            }
        }
        // 前序遍历
        // preTraversal(treeNodes[0]);
        // 中序遍历
        // inTraversal(treeNodes[0]);
        // 后序遍历
        // postTraversal(treeNodes[0]);
        // 层次遍历
        levelTraversal(treeNodes[0]);
    }


    /**
     * 前序遍历
     */
    public static void preTraversal(TreeNode treeNode) {

        if (treeNode == null) {
            return;
        }

        print(treeNode);

        if (treeNode.hasLeftNode()) {
            preTraversal(treeNode.getLeftNode());
        }

        if (treeNode.hasRightNode()) {
            preTraversal(treeNode.getRightNode());
        }
    }

    public static void print(TreeNode treeNode) {
        System.out.println(treeNode.getValue());
    }

    /**
     * 中序遍历
     */
    public static void inTraversal(TreeNode treeNode) {

        if (treeNode == null) {
            return;
        }

        if (treeNode.hasLeftNode()) {
            inTraversal(treeNode.getLeftNode());
        }

        print(treeNode);

        if (treeNode.hasRightNode()) {
            inTraversal(treeNode.getRightNode());
        }
    }

    /**
     * 后序遍历
     */
    public static void postTraversal(TreeNode treeNode) {
        if (treeNode == null) {
            return;
        }

        if (treeNode.hasLeftNode()) {
            inTraversal(treeNode.getLeftNode());
        }

        if (treeNode.hasRightNode()) {
            inTraversal(treeNode.getRightNode());
        }

        print(treeNode);
    }

    /**
     * 层次遍历
     * 层次遍历属于广度优先遍历（DFS）,需要借助队列实现遍历。
     * 前中后遍历属于深度优先遍历（BFS），需要借助栈/递归实现遍历。
     */
    public static void levelTraversal(TreeNode treeNode) {

        if (treeNode == null) {
            return;
        }

        // 借用队列存储叶子节点
        LinkedList list = new LinkedList();
        list.add(treeNode);

        while (!list.isEmpty()) {
            TreeNode node = (TreeNode) list.poll();
            print(node);
            if (node.hasLeftNode())
                list.add(node.getLeftNode());

            if (node.hasRightNode())
                list.add(node.getRightNode());
        }
    }

    /**
     * 树节点
     */
    static final class TreeNode {

        private int value;
        private TreeNode leftNode;
        private TreeNode rightNode;

        /**
         * 是否存在左子树
         *
         * @return
         */
        public boolean hasLeftNode() {
            return this.leftNode != null;
        }

        /**
         * 是否存在右子树
         *
         * @return
         */
        public boolean hasRightNode() {
            return this.rightNode != null;
        }

        /**
         * 是否存在子节点（又或是叶子节点）
         *
         * @return
         */
        public boolean hasChildren() {
            return this.rightNode != null || this.leftNode != null;
        }

        public TreeNode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public TreeNode getLeftNode() {
            return leftNode;
        }

        public void setLeftNode(TreeNode leftNode) {
            this.leftNode = leftNode;
        }

        public TreeNode getRightNode() {
            return rightNode;
        }

        public void setRightNode(TreeNode rightNode) {
            this.rightNode = rightNode;
        }
    }


}
