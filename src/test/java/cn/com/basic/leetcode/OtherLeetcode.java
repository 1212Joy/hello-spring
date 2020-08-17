package cn.com.basic.leetcode;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class OtherLeetcode {


    @Test
    public void lruCache() throws Exception {
        LRUCache lruCache = new LRUCache(2);
        //[1,1],[2,2],[1],[3,3],[2],[4,4],[1],[3],[4]
        lruCache.put(2, 1);
        lruCache.put(2, 2);
        System.out.println(lruCache.get(2));
        lruCache.put(1, 1);
        lruCache.put(4, 1);
        System.out.println(lruCache.get(2));

//        System.out.println(lruCache.get(1));
//        System.out.println(lruCache.get(3));
//        System.out.println(lruCache.get(4));
    }


}

/**
 * #146 - LRU缓存机制
 *
 * 数据结构：哈希+双端列表
 * 思路：a.存在、不存在 b.超出size
 */
class LRUCache {
    //最大能装的元素数量
    int capacity;
    private Map<Integer, LinkListNode> key2node;
    //新增的节点可以通过 head.next赋值
    private LinkListNode head;
    //尾结点删除可以通过 tail.pre获取到
    private LinkListNode tail;


    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.key2node = new HashMap<>();
        //记录头尾节点
        this.head = new LinkListNode(-1);
        this.tail = new LinkListNode(-1);
        //关联两个节点
        head.next = tail;
        tail.pre = head;
    }

    public int get(int key) {
        int res = -1;
        LinkListNode node = key2node.get(key);
        //不存在
        if (node == null) {
            return res;
            //存在
        } else {
            res = node.val;
            move2head(node, node.val);
        }
        return res;
    }

    public void put(int key, int value) {
        LinkListNode node = key2node.get(key);
        //不存在
        if (node == null) {
            add2head(new LinkListNode(key, value));
            //存在
        } else {
            move2head(node, value);
        }
    }

    /**
     * @param oldNode  需要知道原来node的前后节点，才能删除
     * @param newValue 替换场景需要  ！！！
     */
    private void move2head(LinkListNode oldNode, int newValue) {
        //移除当前
        removeNode(oldNode);
        //移动到head
        add2head(new LinkListNode(oldNode.key, newValue));
    }

    //添加到head
    private void add2head(LinkListNode node) {
        //先设置next
        LinkListNode temHead = head.next;
        node.next = temHead;
        head.next = node;
        //再设置pre
        node.pre = head;
        temHead.pre = node; //!!!  最后的就因为这个
        //map重新添加
        key2node.put(node.key, node);
        if (key2node.size() > capacity) {
            //超过最大容量,删除尾部
            removeTail();
        }
    }

    //删除一个指定节点
    private void removeNode(LinkListNode node) {
        //移除map中值
        key2node.remove(node.key);
        //链表删除
        LinkListNode temPre = node.pre;
        LinkListNode temNext = node.next;
        temPre.next = temNext;
        temNext.pre = temPre;
    }

    //删除尾结点
    private void removeTail() {
        LinkListNode temTailBefore = tail.pre.pre;
        //边界判断
        if (temTailBefore != null) {
            //删除map
            key2node.remove(temTailBefore.next.key);
            temTailBefore.next = tail;
            tail.pre = temTailBefore;
            //不用手动释放，没有引用就被回收了
        }

    }
}

class LinkListNode {
    Integer key;
    int val;
    LinkListNode pre;
    LinkListNode next;

    public LinkListNode(int val) {
        this.val = val;
    }

    public LinkListNode(int key, int val) {
        this.key = key;
        this.val = val;
    }
}
