//给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
//
//
//
//
// 示例 1：
//
//
//输入：head = [1,2,3,4,5]
//输出：[5,4,3,2,1]
//
//
// 示例 2：
//
//
//输入：head = [1,2]
//输出：[2,1]
//
//
// 示例 3：
//
//
//输入：head = []
//输出：[]
//
//
//
//
// 提示：
//
//
// 链表中节点的数目范围是 [0, 5000]
// -5000 <= Node.val <= 5000
//
//
//
//
// 进阶：链表可以选用迭代或递归方式完成反转。你能否用两种方法解决这道题？
//
//
// Related Topics 递归 链表
// 👍 1886 👎 0


package com.lvyongwenhouzi.server.leetcode.editor.cn;

public class ReverseLinkedList {
    public static void main(String[] args) {

        Solution solution = new ReverseLinkedList().new Solution();

        ListNode listNode1 = new ListNode(5, null);
        ListNode listNode2 = new ListNode(4, listNode1);
        ListNode listNode3 = new ListNode(3, listNode2);
        ListNode listNode4 = new ListNode(2, listNode3);
        ListNode listNode5 = new ListNode(1, listNode4);

        ListNode reverse = solution.reverseList(listNode5);

        System.out.println(solution.toString(reverse));
    }
    //leetcode submit region begin(Prohibit modification and deletion)

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode() {}
     * ListNode(int val) { this.val = val; }
     * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */
    class Solution {

        /**
         * 思路：
         * 1、由于是单向链表在遍历当前节点的时候无法回溯前置节点所以需要记录前置节点
         * 2、单链表只有一个指针当反向指向前需要保留原有指向后置节点的内容
         *
         * @param head
         * @return
         */
        public ListNode reverseList(ListNode head) {

            if (head == null || head.next == null) return head;

            ListNode pre = null;
            ListNode curr = head;
            while (curr != null) {
                ListNode next = curr.next;
                curr.next = pre;
                pre = curr;
                curr = next;
            }
            return pre;
        }

        public String toString(ListNode head) {

            String str = "";
            ListNode curr = head;
            while (curr != null) {
                str += String.valueOf(curr.val);
                curr = curr.next;
            }

            return str;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

}
