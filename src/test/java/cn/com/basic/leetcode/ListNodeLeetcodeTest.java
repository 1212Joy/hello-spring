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

