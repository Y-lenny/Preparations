//给你一个链表数组，每个链表都已经按升序排列。
//
// 请你将所有链表合并到一个升序链表中，返回合并后的链表。
//
//
//
// 示例 1：
//
// 输入：lists = [[1,4,5],[1,3,4],[2,6]]
//输出：[1,1,2,3,4,4,5,6]
//解释：链表数组如下：
//[
//  1->4->5,
//  1->3->4,
//  2->6
//]
//将它们合并到一个有序链表中得到。
//1->1->2->3->4->4->5->6
//
//
// 示例 2：
//
// 输入：lists = []
//输出：[]
//
//
// 示例 3：
//
// 输入：lists = [[]]
//输出：[]
//
//
//
//
// 提示：
//
//
// k == lists.length
// 0 <= k <= 10^4
// 0 <= lists[i].length <= 500
// -10^4 <= lists[i][j] <= 10^4
// lists[i] 按 升序 排列
// lists[i].length 的总和不超过 10^4
//
// Related Topics 链表 分治 堆（优先队列） 归并排序
// 👍 1603 👎 0


package com.lvyongwenhouzi.server.leetcode.editor.cn;

public class MergeKSortedLists {
    public static void main(String[] args) {
        Solution solution = new MergeKSortedLists().new Solution();
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

        public ListNode mergeKLists(ListNode[] lists) {
            return split(lists, 0, lists.length - 1);
        }

        private ListNode split(ListNode[] lists, int i, int j) {
            if (i == j) {
                return lists[i];
            }
            if (i > j) {
                return null;
            }
            int k = (i + j) >> 1;
            ListNode listNode1 = split(lists, i, k);
            ListNode listNode2 = split(lists, k + 1, j);
            return merge(listNode1, listNode2);
        }

        private ListNode merge(ListNode listNode1, ListNode listNode2) {
            if (listNode1 == null || listNode2 == null) {
                return listNode1 == null ? listNode2 : listNode1;
            }
            ListNode head = new ListNode(), tail = head, listNode1P = listNode1, listNode2P = listNode2;
            while (listNode1P != null && listNode2P != null) {
                if (listNode1P.val <= listNode2P.val) {
                    tail.next = listNode1P;
                    listNode1P = listNode1P.next;
                } else {
                    tail.next = listNode2P;
                    listNode2P = listNode2P.next;
                }
                tail = tail.next;
            }

            ListNode append = listNode1P == null ? listNode2P : listNode1P;
            tail.next = append;
            return head.next;
        }


        // Definition for singly-linked list.
        public class ListNode {
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
//leetcode submit region end(Prohibit modification and deletion)

}
