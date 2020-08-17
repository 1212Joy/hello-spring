package cn.com.basic.leetcode;

import org.junit.Test;

/**
 * Created by zhaijiayi on 2020/5/6.
 */
public class ListNodeLeetcodeTest {


    @Test
    public void test() throws Exception {

    }

    /**
     * 206. 反转链表
     *
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        while (current != null) {
            ListNode tem = current.next;
            current.next = prev;
            prev = current;
            current = tem;//别漏了这一句！！
        }
        return prev;  //return current;  返回的不是current，而是pre
    }

    /**
     * 21. 合并两个有序链表
     * 实现：遍历、递归
     *
     * @param l1
     * @param l2
     * @return
     */
    @Test
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        return mergeTwoLists_foreach(l1, l2);
    }

    //遍历
    public ListNode mergeTwoLists_foreach(ListNode l1, ListNode l2) {
        //新建一个哨兵节点
        ListNode preHead = new ListNode(-1);
        ListNode pre = preHead; // ListNode pre = null;
        while (!(l1 == null || l2 == null)) {
            if (l1.val <= l2.val) {
                pre.next = l1;
                l1 = l1.next;
            } else {
                pre.next = l2;
                l2 = l2.next;
            }
            pre = pre.next;
        }
        //别漏了！！ 合并后 l1 和 l2 最多只有一个还未被合并完，我们直接将链表末尾指向未合并完的链表即可
        pre.next = l1 == null ? l2 : l1;
        return preHead.next;
    }

    //递归
    public ListNode mergeTwoLists_recursion(ListNode l1, ListNode l2) {
        // 终止条件：两条链表分别名为 l1 和 l2，当 l1 为空或 l2 为空时结束
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        //本级递归内容：如果 l1 的 val 值更小，则将 l1.next 与排序好的链表头相接，l2 同理
        if (l1.val < l2.val) {
            //指向小的值
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else {
            //指向小的值
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
        //返回值：每一层调用都返回排序好的链表头

    }

    /**
     * 2. 两数相加
     * <p>
     * 题意：
     * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 0 -> 8
     * 原因：342 + 465 = 807
     * <p>
     * 思路：
     * a.两个链表长度不同时，则进行补0
     * b.carry的意义-存在进位且最多只会进一位
     * 每一位计算的同时需要考虑上一位的进位问题，而当前位计算结束后同样需要更新进位值
     * 如果两个链表全部遍历完毕后，进位值为 11，则在新链表最前方添加节点 11
     * <p>
     * 复杂度：
     * 时间复杂度：O(\max(m, n))，
     * 空间复杂度：O(\max(m, n))， 新列表的长度最多为 \max(m,n) + 1max(m,n)+1。
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        //构建一个哨兵节点
        ListNode pre = new ListNode(0);
        ListNode cur = pre;
        int carry = 0;
        //两个节点都不为空时跳出循环
        while (l1 != null || l2 != null) {
            int x = l1 == null ? 0 : l1.val;
            int y = l2 == null ? 0 : l2.val;
            //为啥加carry是干嘛的？上一次计算的进位
            int sum = x + y + carry;
            //这个啥意思 - 考虑进位问题
            carry = sum / 10;
            //加和取余，设置当前节点值
            sum = sum % 10;
            //创建一个新的节点
            cur.next = new ListNode(sum);
            //当前节点移动到新节点上
            cur = cur.next;
            //遍历节点
            if (l1 != null)
                l1 = l1.next;
            if (l2 != null)
                l2 = l2.next;
        }
        //这个啥意思
        if (carry == 1) {
            cur.next = new ListNode(carry);
        }
        return pre.next;

    }

}


class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}

