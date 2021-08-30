package com.lvyongwenhouzi.server.structure;

/**
 * 字符串用单链表存储，如何来判断是一个回文串？时间空间复杂度是多少？
 *
 * @author lvyongwen
 */
public class PalindromeStrBySingleLinkedList {

    public static void main(String[] args) {

        SingleLinkList singleLinkList = new SingleLinkList();
        singleLinkList.add('n');
        singleLinkList.add('o');
        singleLinkList.add('o');
        singleLinkList.add('n');

        System.out.println(singleLinkList);
        System.out.println(singleLinkList.isPalindromeStr());
    }

    /**
     * 单链表
     */
    public static class SingleLinkList {

        private Node first;

        public void add(char c) {
            if (first == null){
                first = new Node();
                first.setData(c);
                return;
            }
            Node node = new Node();
            node.setData(c);
            Node tempNode = first;
            while (hasNext(tempNode)) {
                tempNode = tempNode.next;
            }
            tempNode.next = node;
        }

        public boolean hasNext(Node node) {
            return node.next != null;
        }

        /**
         * 判断是否是回文字符串
         * T(n) = O(n)
         * @return boolean
         */
        public boolean isPalindromeStr(){

            Node tempNode = first;
            String norStr = tempNode != null ? Character.toString(tempNode.data) : null;
            String revStr = tempNode != null ? Character.toString(tempNode.data) : null;

            while (hasNext(tempNode)){
                tempNode = tempNode.next;
                norStr  = norStr + tempNode.data;
                revStr  = tempNode.data + revStr;
            }

            if (norStr.equalsIgnoreCase(revStr)){
                return true;
            }
            return false;
        }

        public Node getFirst() {
            return first;
        }

        public void setFirst(Node first) {
            this.first = first;
        }

        @Override
        public String toString() {

            String str = "";
            Node tempNode = null;
            tempNode = first;
            str = tempNode != null ? Character.toString(tempNode.data) : null;
            while (hasNext(tempNode)){
                tempNode = tempNode.next;
                str+= Character.toString(tempNode.data);
            }
            return "SingleLinkList{" +
                    "toString =" + str +
                    '}';
        }
    }

    /**
     * 单链表节点
     */
    public static class Node {

        private char data;

        private Node next;

        public char getData() {
            return data;
        }

        public void setData(char data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

}
