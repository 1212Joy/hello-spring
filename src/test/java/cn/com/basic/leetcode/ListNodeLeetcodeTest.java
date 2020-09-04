package cn.com.basic.leetcode;

import org.junit.Test;

import java.util.*;

/**
 * Created by zhaijiayi on 2020/5/6.
 */
public class ListNodeLeetcodeTest {


    @Test
    public void test() throws Exception {

    }

    /**
     * 206. 反转链表
     * 方法1 - 迭代
     * 方法2 - 递归
     *
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        return null;
    }

    //遍历方式 - 维护一个pre、current
    private ListNode reverseList_foreach(ListNode head) {
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

    //递归方式
    private ListNode reverseList_recursion(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode prev = reverseList_recursion(head.next);
        head.next.next = head;//head.next.next=prev;   错的地方
        head.next = null;
        return prev;
    }

    /**
     * 92. 反转链表 II
     * 部分链表反转
     * 反转从位置 m 到 n 的链表。请使用一趟扫描完成反转。
     * 1 ≤ m ≤ n ≤ 链表长度。
     * 解题：
     * * 方法1 - 迭代
     * <p>
     * 时间复杂度: O(N)O(N)。考虑包含 NN 个结点的链表。对每个节点最多会处理
     * （第 nn 个结点之后的结点不处理）。
     * 空间复杂度: O(1)O(1)。我们仅仅在原有链表的基础上调整了一些指针，只使用了 O(1)O(1) 的额外存储空间来获得结果。
     * <p>
     * <p>
     * * 方法2 - 递归
     *
     * @param head
     * @param m
     * @param n
     * @return
     */
    public ListNode reverseBetween(ListNode head, int m, int n) {
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;

        ListNode g = dummyHead;
        ListNode p = dummyHead.next;

        int step = 0;

        while (step < m - 1) {
            g = g.next;
            p = p.next;
            step++;
        }

        for (int i = 0; i < n - m; i++) {
            ListNode removed = p.next;
            p.next = p.next.next;

            removed.next = g.next;
            g.next = removed;
        }

        return dummyHead.next;
    }

    /**
     * 25. K 个一组翻转链表
     * <p>
     * <p>
     * 时间复杂度为 O(n*K)O(n∗K) 最好的情况为 O(n)O(n) 最差的情况未 O(n^2)O(n
     * 2
     * )
     * 空间复杂度为 O(1)O(1) 除了几个必须的节点指针外，我们并没有占用其他空间
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }
        //定义一个假的节点。
        ListNode dummy = new ListNode(0);
        //假节点的next指向head。
        // dummy->1->2->3->4->5
        dummy.next = head;
        //初始化pre和end都指向dummy。pre指每次要翻转的链表的头结点的上一个节点。end指每次要翻转的链表的尾节点
        ListNode start = dummy;
        ListNode end = dummy;

        while (end.next != null) {
            //循环k次，找到需要翻转的链表的结尾,这里每次循环要判断end是否等于空,因为如果为空，end.next会报空指针异常。
            //dummy->1->2->3->4->5 若k为2，循环2次，end指向2
            for (int i = 0; i < k && end != null; i++) {
                end = end.next;
            }
            //如果end==null，即需要翻转的链表的节点数小于k，不执行翻转。
            if (end == null) {
                break;
            }
            //先记录下end.next,方便后面链接链表
            ListNode next = end.next;
            //然后断开链表
            end.next = null;
            //记录下要翻转链表的头节点
            ListNode temp = start.next;
            //翻转链表,pre.next指向翻转后的链表。1->2 变成2->1。 dummy->2->1
            start.next = reverse(temp);
            //翻转后头节点变到最后。通过.next把断开的链表重新链接。
            temp.next = next;
            //将pre换成下次要翻转的链表的头结点的上一个节点。即start
            start = temp;
            //翻转结束，将end置为下次要翻转的链表的头结点的上一个节点。即start
            end = temp;
        }
        return dummy.next;


    }

    //链表翻转
    // 例子：   head： 1->2->3->4
    public ListNode reverse(ListNode head) {
        //单链表为空或只有一个节点，直接返回原单链表
        if (head == null || head.next == null) {
            return head;
        }
        //前一个节点指针
        ListNode preNode = null;
        //当前节点指针
        ListNode curNode = head;
        //下一个节点指针
        ListNode nextNode = null;
        while (curNode != null) {
            nextNode = curNode.next;//nextNode 指向下一个节点,保存当前节点后面的链表。
            curNode.next = preNode;//将当前节点next域指向前一个节点   null<-1<-2<-3<-4
            preNode = curNode;//preNode 指针向后移动。preNode指向当前节点。
            curNode = nextNode;//curNode指针向后移动。下一个节点变成当前节点
        }
        return preNode;

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
    private ListNode mergeTwoLists_foreach(ListNode l1, ListNode l2) {
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
     * 23. 合并K个升序链表
     * 优先队列,先全部排序，再主意构建链表
     * <p>
     * 每次 O(logK)O(logK) 比较 K个指针求 min, 时间复杂度：O(NlogK)O(NlogK)
     *
     * @param lists
     * @return
     */
    public ListNode mergeKLists_Queue(ListNode[] lists) {
        //构造函数添加比较器 从小到大
        Queue<ListNode> pq = new PriorityQueue<>((v1, v2) -> v1.val - v2.val);
        //初始化全部头节点
        for (ListNode node : lists) {
            if (node != null) {
                pq.offer(node);
            }
        }

        ListNode dummyHead = new ListNode(0);
        //每次指向当前最大的
        ListNode tail = dummyHead;
        while (!pq.isEmpty()) {
            //取出找到当前最小的的，添加到队列
            ListNode minNode = pq.poll();
            tail.next = minNode;
            tail = minNode;
            if (minNode.next != null) {
                //添加新一轮的数据
                pq.offer(minNode.next);
            }
        }

        return dummyHead.next;
    }

    /**
     * 每次 O(K)O(K) 比较 K个指针求 min, 时间复杂度：O(NK)O(NK)
     *
     * @param lists
     * @return
     */
    public ListNode mergeKLists_2(ListNode[] lists) {
        int k = lists.length;
        ListNode dummyHead = new ListNode(0);
        ListNode tail = dummyHead;
        while (true) {
            //比较 K个指针求 min   node值
            ListNode minNode = null;
            //最小值
            int minPointer = -1;
            //每个节点的筛选，都需要遍历全部k个节点
            for (int i = 0; i < k; i++) {
                if (lists[i] == null) {
                    continue;
                }
                if (minNode == null || lists[i].val < minNode.val) {
                    minNode = lists[i];
                    minPointer = i;
                }
            }
            if (minPointer == -1) {
                break;
            }
            tail.next = minNode;
            tail = tail.next;
            lists[minPointer] = lists[minPointer].next;
        }
        return dummyHead.next;

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

    /**
     * 445. 两数相加 II
     * 链表存储的顺序是从高位到低位，两数相加的话我们就需要从低位开始计算到高位。所以这里存在着相反的顺序关系。
     * <p>
     * 这种需要用到逆序的思路可以用栈的特性来解决此类问题
     * 此外还需要注意的点有：
     * 1. 题目要求输入链表不能修改，所以这里增加2个引用来存储两个链表。
     * <p>
     * O(max(M,N))
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers_II(ListNode l1, ListNode l2) {

        ListNode head = new ListNode(-1);
        ListNode p = l1, q = l2;
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();
        while (p != null) {
            stack1.push(p.val);
            p = p.next;
        }
        while (q != null) {
            stack2.push(q.val);
            q = q.next;
        }
        int carry = 0;
        while (!stack1.empty() || !stack2.empty() || carry != 0) {
            int data1 = stack1.empty() ? 0 : stack1.pop();
            int data2 = stack2.empty() ? 0 : stack2.pop();
            ListNode node = new ListNode((carry + data1 + data2) % 10);
            carry = (carry + data1 + data2) / 10;
            node.next = head.next;
            head.next = node;
        }
        return head.next;
    }


    /**
     * 148. 排序链表
     * todo 把双路归并和 cut 断链的代码
     * 想到二分法，从而联想到归并排序
     * <p>
     * 在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序。
     * 解题：
     * 1.归并排序（递归法）
     * 2.归并排序（从底至顶直接合并）
     *
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode fast = head.next, slow = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode tmp = slow.next;
        slow.next = null;
        //找到中间节点，进行左右分别递归排序，再合并返回
        ListNode left = sortList(head);
        ListNode right = sortList(tmp);
        //将内部排序
        ListNode h = new ListNode(0);
        //这个值必须有
        ListNode res = h;
        while (left != null && right != null) {
            if (left.val < right.val) {
                h.next = left;
                left = left.next;
            } else {
                h.next = right;
                right = right.next;
            }
            h = h.next;
        }
        h.next = left != null ? left : right;
        return res.next;
    }

    /**
     * 19. 删除链表的倒数第N个节点
     * 解题：
     * 两个指针、添加一个哨兵
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode preHead = new ListNode(-1);
        preHead.next = head;
        ListNode slow = preHead;
        ListNode fast = preHead;//n最小是0，所以从哨兵结点开始
        for (int i = 0; i <= n; i++) {//<=n
            fast = fast.next;
        }
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        //倒数第n-1个结点=当前slow
        ListNode beforeNNode = slow;
        beforeNNode.next = slow.next.next;//两个next
        return preHead.next;
    }

    /**
     * 24. 两两交换链表中的节点
     * 递归
     *
     * @param head
     * @return
     */
    public ListNode swapPairs(ListNode head) {

        //停止退出条件
        if (head == null || head.next == null) {
            return head;
        }
        //调用递归，传递给当前。返回舱
        ListNode newHead = head.next;
        //返回交换后的列表头结点
        head.next = swapPairs(newHead.next);
        //第一个点和newNext替换
        newHead.next = head;
        //?整个心链表的第一个点咋返回=》想一下栈的最上层
        return newHead;
    }

    /**
     * 143. 重排链表
     * 给定一个单链表 L：L0→L1→…→Ln-1→Ln ，
     * 将其重新排列后变为： L0→Ln→L1→Ln-1→L2→Ln-2→…
     * <p>
     * 思路：找到中间节点，再reverse一下，在合并
     *
     * @param head
     */
    public void reorderList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return;
        }
        //找中点，链表分成两个
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode newHead = slow.next;
        slow.next = null;

        //第二个链表倒置
        newHead = reverseList(newHead);

        //链表节点依次连接
        while (newHead != null) {
            ListNode temp = newHead.next;
            newHead.next = head.next;
            head.next = newHead;
            head = newHead.next;
            newHead = temp;
        }

    }

    /**
     * 83. 删除排序链表中的重复元素
     * 简单的，重复的元素保留一个
     * <p>
     * 双指针
     *
     * @param head
     * @return  返回去掉重复的值（与82返回值不同）
     */
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null)
            return head;
        ListNode pre = head;//指向第一个不重复的节点
        ListNode cur = head.next;//当前节点
        while (cur != null) {
            if (cur.val != pre.val) {//找到了一个新的不重复节点，则把pre指向cur，更新pre
                pre.next = cur;
                pre = cur;
            }
            cur = cur.next;//更新cur
        }
        pre.next = null;//切断pre与剩余重复元素的联系
        return head;
    }

    /**
     * 82. 删除排序链表中的重复元素 II
     *
     * 重复的元素都去掉
     * <p>
     * 链表是排序的，所以重复的总是连续的
     * <p>
     * https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list-ii/solution/javashuang-zhi-zhen-dai-ma-jiao-duan-rong-yi-li-ji/
     * 直接找不重复的值。基本思路就是每一次区间[l,r)（左闭右开）中的数字相同，然后判断该区间的长度是否为1，若长度为1则通过尾插法插入到答案中
     * 尾插法双指针的巧妙结合
     *
     * @param head
     * @return  返回不重复的值
     */
    public ListNode deleteDuplicates_II(ListNode head) {
        if (head == null) return head;  // 若head为空则直接返回null
        ListNode dummy = new ListNode(-1);  // 建立一个虚拟头结点
        // 定义一个尾巴，用于尾插法。一直指向链表的尾部
        ListNode tail = dummy;
        for (ListNode l = head, r = head; l != null; l = r) {
            // 只要r不为空并且与l的值相等则一直向后移动
            while (r != null && r.val == l.val)
                r = r.next;
            //若长度为1，则通过尾插法加入。
            if (l.next == r) {
                tail.next = l;  // 基本的尾插法
                tail = l;
                tail.next = null;  // 这里记得将尾部的后面置为null，不然可能后面会带着一些其他的节点。
            }
        }
        return dummy.next;
    }

    /**
     * 141. 环形链表
     *
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {

        //定义两个不同速度的指针
        ListNode fast = head;
        ListNode slow = head;
        //为空链表、到达链表的尾部退出
        while (!(slow == null || fast == null || fast.next == null)) {//别漏了！ fast.next==null
            //怎么标识步长
            slow = slow.next;
            fast = fast.next.next;
            //如果遇到，则是环
            if (fast == slow) {
                return true;
            }

        }
        return false;
    }

    /**
     * 142. 环形链表 II
     * O(n)
     * a（头->相遇的节点）+b（环内的节点）
     * <p>
     * 第二次相遇分析：
     * slow指针 位置不变 ，将fast指针重新 指向链表头部节点 ；slow和fast同时每轮向前走 11 步；
     * TIPS：此时 f = 0f=0，s = nbs=nb ；
     * <p>
     * 当 fast 指针走到f = af=a 步时，slow 指针走到步s = a+nbs=a+nb，此时 两指针重合，并同时指向链表环入口 。
     *
     * @param head
     * @return https://leetcode-cn.com/problems/linked-list-cycle-ii/solution/linked-list-cycle-ii-kuai-man-zhi-zhen-shuang-zhi-/
     */
    public ListNode detectCycle(ListNode head) {
        ListNode fast = head, slow = head;
        while (true) {
            if (fast == null || fast.next == null) return null;
            fast = fast.next.next;
            slow = slow.next;
            //此时，fast比slow多走nb步
            if (fast == slow) break;
        }
        //此时fast从头走a步就会和slow相遇在第一个节点？？为啥
        fast = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return fast;

    }

    /**
     * todo 160. 相交链表  看不懂
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {

        ListNode ha = headA, hb = headB;
        while (ha != hb) {
            ha = ha != null ? ha.next : headB;
            hb = hb != null ? hb.next : headA;
        }
        return ha;
    }

}


class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}

