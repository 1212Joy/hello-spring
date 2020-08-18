package cn.com.basic.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by zhaijiayi on 2020/5/6.
 */
public class TreeLeetcodeTest {


    @Test
    public void test() throws Exception {

    }

    /**
     * 199. 二叉树的右视图
     * <p>
     * 解题1-深度优先 DFS
     * 解题2-广度优先 BFS
     *
     * @param root
     * @return
     */
    List<Integer> res;

    public List<Integer> rightSideView(TreeNode root) {

        //深度优先 -rightSideView_dfs
        //广度优先 - rightSideView_bfs
        return res;
    }

    public List<Integer> rightSideView_bfs(TreeNode root) {
        res = new ArrayList<>();
        if (root == null) return res;
        //从右到左每一排打印然后只记录最右边得值
        //！！！用队列记录所有节点
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            //！！！记录当前行
            Integer currentLevelSize = queue.size();
            for (int i = 0; i < currentLevelSize; i++) {
                TreeNode node = queue.poll();
                //只记录最右节点值
                if (i == 0) {
                    res.add(node.val);
                }
                //先放右节点，再方左节点
                if (node.right != null) {
                    queue.add(node.right);
                }
                if (node.left != null) {
                    queue.add(node.left);
                }

            }
        }

        return res;
    }

    private List<Integer> rightSideView_dfs(TreeNode root) {
        res = new ArrayList<>();
        // ！！！从根节点开始访问，根节点深度是0
        dfs(root, 0);
        return res;
    }

    private void dfs(TreeNode root, int depth) {
        if (root == null) {
            return;
        }
        // 先访问 当前节点，再递归地访问 右子树 和 左子树。
        if (depth == res.size()) {   // 如果当前节点所在深度还没有出现在res里，说明在该深度下当前节点是第一个被访问的节点，因此将当前节点加入res中。
            res.add(root.val);
        }
        depth++;
        dfs(root.right, depth);
        dfs(root.left, depth);
    }

    /**
     * 102. 二叉树的层序遍历
     * 广度优先
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        //LinkedList是一种双端队列,和Deque有啥区别
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            List<Integer> currentLevel = new ArrayList<>();
            Integer currentLevelSize = queue.size();
            for (int i = 0; i < currentLevelSize; i++) {
                //取出第一层元素，将左右节点放到当前队列中
                TreeNode current = queue.poll();
                currentLevel.add(current.val);
                if (current.left != null) {
                    queue.add(current.left);
                }
                if (current.right != null) {
                    queue.add(current.right);
                }
            }
            res.add(currentLevel);
        }

        return res;
    }
    /**
     * 103. 二叉树的锯齿形层次遍历
     * 遍历顺序：先左到右，再右到左
     * 思路：
     * 广度优先，和层序便利操作类似，不过需要使用双端队列保存树的节点
     * 同时维护一个left2right，每一层后更改
     *
     * @param root
     * @return
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        //边界判断
        if (root == null) return res;
        //按层遍历
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        boolean left2right = true;
        while (!queue.isEmpty()) {
            LinkedList<Integer> current = new LinkedList<>();
            //内部还需要遍历
            Integer currentSize = queue.size();
            for (int i = 0; i < currentSize; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
                //！！！收尾别放饭了
                if (left2right) {
                    current.addLast(node.val);
                } else {
                    current.addFirst(node.val);
                }

            }
            left2right = !left2right;
            res.add(current);

        }

        return res;

    }


    /**
     * todo 105. 从前序与中序遍历序列构造二叉树  没看懂
     * 根据一棵树的前序遍历与中序遍历构造二叉树。
     * <p>
     * 前序遍历：[ 根节点, [左子树的前序遍历结果], [右子树的前序遍历结果] ]
     * 中序遍历：[ [左子树的中序遍历结果], 根节点, [右子树的中序遍历结果] ]
     * 解题思路：
     * 1.递归 :通过额外的map记录
     * 2.迭代:通过一个栈记录
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildTree_2(preorder, inorder);
    }

    private Map<Integer, Integer> indexMap;

    public TreeNode buildTree_1(int[] preorder, int[] inorder) {
        int n = preorder.length;
        // 构造哈希映射，帮助我们快速定位根节点
        indexMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < n; i++) {
            indexMap.put(inorder[i], i);
        }
        return myBuildTree(preorder, inorder, 0, n - 1, 0, n - 1);

    }

    public TreeNode myBuildTree(int[] preorder, int[] inorder, int preorder_left, int preorder_right, int inorder_left, int inorder_right) {
        if (preorder_left > preorder_right) {
            return null;
        }

        // 前序遍历中的第一个节点就是根节点
        int preorder_root = preorder_left;
        // 在中序遍历中定位根节点
        int inorder_root = indexMap.get(preorder[preorder_root]);

        // 先把根节点建立出来
        TreeNode root = new TreeNode(preorder[preorder_root]);
        // 得到左子树中的节点数目
        int size_left_subtree = inorder_root - inorder_left;
        // 递归地构造左子树，并连接到根节点
        // 先序遍历中「从 左边界+1 开始的 size_left_subtree」个元素就对应了中序遍历中「从 左边界 开始到 根节点定位-1」的元素
        root.left = myBuildTree(preorder, inorder, preorder_left + 1, preorder_left + size_left_subtree, inorder_left, inorder_root - 1);
        // 递归地构造右子树，并连接到根节点
        // 先序遍历中「从 左边界+1+左子树节点数目 开始到 右边界」的元素就对应了中序遍历中「从 根节点定位+1 到 右边界」的元素
        root.right = myBuildTree(preorder, inorder, preorder_left + size_left_subtree + 1, preorder_right, inorder_root + 1, inorder_right);
        return root;
    }


    public TreeNode buildTree_2(int[] preorder, int[] inorder) {
        if (preorder == null || preorder.length == 0) {
            return null;
        }
        //先通过前序遍历把根节点找到
        TreeNode root = new TreeNode(preorder[0]);
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(root);
        int inorderIndex = 0;
        for (int i = 1; i < preorder.length; i++) {
            int preorderVal = preorder[i];
            TreeNode node = stack.peek();
            if (node.val != inorder[inorderIndex]) {
                node.left = new TreeNode(preorderVal);
                stack.push(node.left);
            } else {
                while (!stack.isEmpty() && stack.peek().val == inorder[inorderIndex]) {
                    node = stack.pop();
                    inorderIndex++;
                }
                node.right = new TreeNode(preorderVal);
                stack.push(node.right);
            }
        }
        return root;

    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}