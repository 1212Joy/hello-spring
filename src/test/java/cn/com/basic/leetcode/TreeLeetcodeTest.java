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

    public List<Integer> rightSideView_dfs(TreeNode root) {
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


}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}