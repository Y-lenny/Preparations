package com.lvyongwenhouzi.structure;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 问题：如何实现链表的反转 ?
 * 思路：
 * 链表有虚拟表头（前提）
 * 1、遍历需要反转的链表，新创建一个虚拟表头并判断是否有后置节点
 * 有：插入节点指向新表头后面节点，并插入表头next位置
 * 没有：直接插入表头next位置
 *
 * 技巧：
 * 插入反转链表
 *
 * leetcode：{@link com.lvyongwenhouzi.leetcode.editor.cn.ReverseLinkedList}
 */
public class ReverseLinkedList {

    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        linkedList.add(4);
        linkedList.add(5);

        // 反转链表
        linkedList.reverse();

        System.out.println(linkedList.toString());
    }

    public static class LinkedList {


        /**
         * 反转链表
         */
        private void reverse() {

            if (head.next == null) {
                return;
            }

            Node newHead = new Node(null, null);
            Node node = head;
            while (node.hasNext()) {
                Node moveNode = node.next;
                head.next = moveNode.next;
                if (newHead.hasNext()) {
                    // 插入链表
                    moveNode.next = newHead.next;
                    newHead.next = moveNode;
                } else {
                    moveNode.next = null;
                    newHead.next = moveNode;
                }
            }
            head = newHead;
        }

        public String toString() {

            if (head.next == null) {
                return "";
            }

            String str = "";
            Node node = head;
            while (node.hasNext()) {
                str += node.next.data;
                node = node.next;
            }
            return str;
        }

        /**
         * 头节点
         */
        private Node head;

        public LinkedList() {

            head = new Node(null, null);
        }


        public void add(Object data) {

            Node node = new Node(data, null);
            if (head.next == null) {
                head.next = node;
                return;
            }

            Node tempNode = head;
            while (tempNode.hasNext()) {
                tempNode = tempNode.next;
            }
            tempNode.next = node;
        }
    }


    @AllArgsConstructor
    @Data
    public static class Node {

        private Object data;

        private Node next;

        public boolean hasNext() {
            return next != null;
        }

    }


}
