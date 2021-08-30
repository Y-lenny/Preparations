package com.lvyongwenhouzi.server.structure;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedHashMap;

/**
 * 如何实现LRU缓存淘汰算法?
 * 1、判断是否存在链表当中 ？
 * 存在：删除节点并移至表头
 * 不存在：
 * 2、判断链表是否已满？
 * 满：删除表尾并插入表头
 * 不满：插入表头
 *
 * 技巧：
 * 1、定一个头节点
 * 2、使用node-next.data进行节点是否存在判断（因为单链表无法回朔）
 *
 * Java 集合实现：{@link LinkedHashMap}
 */
public class LRU {

    public static void main(String[] args) {

        LRULinkedList list = new LRULinkedList(4);

        list.add(new Node("1", null));
        list.add(new Node("2", null));
        list.add(new Node("3", null));
        list.add(new Node("4", null));
        list.add(new Node("1", null));
        list.add(new Node("6", null));

        System.out.println(list.toString());
    }

    @Data
    public static class LRULinkedList {

        private Node head = null;

        private Integer length;

        private Integer count = 0;

        public LRULinkedList(Integer length) {
            head = new Node(null, null);
            this.length = length;
        }

        public boolean add(Node node) {

            if (count == 0) {
                node.next = head.next;
                head.next = node;
                count++;
                return true;
            }

            // 1、判断是否存在链表当中 ？
            Node found = find(node);
            if (found != null) {
                Node foundNext = found.next;
                found.next = found.next.next;
                foundNext.next = head.next;
                head.next = foundNext;
            } else {
                // 2、判断链表是否已满？
                if (count >= length) {
                    // 删除末尾节点
                    Node find = head;
                    while (find.hasNext()) {
                        if (find.next.next == null) {
                            find.next = null;
                            count--;
                        } else {
                            find = find.next;
                        }
                    }
                }
                // 插入
                node.next = head.next;
                head.next = node;
                count++;
            }
            return false;
        }

        /**
         * 查询插入的节点的前置节点位置
         *
         * @param node
         * @return
         */
        private Node find(Node node) {

            Node find = head;
            while (find.hasNext()) {
                if (find.next.data.equals(node.data)) {
                    return find;
                } else {
                    find = find.next;
                }
            }

            return null;
        }

        public String toString() {

            String str = null;
            Node node = head;
            while (node.hasNext()) {
                node = node.next;
                str += node.data;
            }

            return str;
        }

    }


    /**
     * 节点元素
     */
    @Data
    @AllArgsConstructor
    public static class Node {

        private Object data;

        private Node next;

        public boolean hasNext() {

            return this.next != null;
        }

        public Node next() {
            return next;
        }
    }


}
