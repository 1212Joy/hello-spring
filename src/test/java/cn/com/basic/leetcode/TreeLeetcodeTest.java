package cn.com.basic.leetcode;

import org.junit.Test;

import java.util.*;

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

    private List<Integer> rightSideView_bfs(TreeNode root) {
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
                //！！！收尾别放反了
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

    private TreeNode buildTree_1(int[] preorder, int[] inorder) {
        int n = preorder.length;
        // 构造哈希映射，帮助我们快速定位根节点
        indexMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < n; i++) {
            indexMap.put(inorder[i], i);
        }
        return myBuildTree(preorder, inorder, 0, n - 1, 0, n - 1);

    }

    private TreeNode myBuildTree(int[] preorder, int[] inorder, int preorder_left, int preorder_right, int inorder_left, int inorder_right) {
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


    private TreeNode buildTree_2(int[] preorder, int[] inorder) {
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

    /**
     * 94. 二叉树的中序遍历
     * 中序遍历： [左][根][右]
     * 解题：递归
     *
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        //递归调用
        inorderTraversal_helper(root, res);
        return res;
    }

    private void inorderTraversal_helper(TreeNode root, List<Integer> res) {
        if (root == null) return;
        //先添加全部左节点
        if (root.left != null) {
            inorderTraversal_helper(root.left, res);
        }
        //！！！添加根节点的值
        res.add(root.val);
        //添加右节点
        if (root.right != null) {
            inorderTraversal_helper(root.right, res);
        }
    }

    /**
     * 112. 路径总和
     * <p>
     * 1:深度优先、递归
     *
     * @param root
     * @param sum
     * @return
     */
    public boolean hasPathSum(TreeNode root, int sum) {
        return hasPathSum_dfs(root, sum);
    }

    boolean hasPathSum_dfs(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        // 到达叶子节点时，递归终止，判断 sum 是否符合条件。
        if (root.left == null && root.right == null) {
            return root.val == sum;
        }
        // 递归地判断root节点的左孩子和右孩子。
        return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
    }

    /**
     * 144. 二叉树的前序遍历
     *
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        preorderTraversal(root, res);
        return res;


    }

    private void preorderTraversal(TreeNode root, List<Integer> res) {
        if (root != null) {
            res.add(root.val);
            if (root.left != null) {
                preorderTraversal(root.left, res);
            }
            if (root.right != null) {
                preorderTraversal(root.right, res);
            }
        }
    }

    /**
     * 543. 二叉树的直径
     * 任意两个节点的最边数
     * 这条路径可能穿过也可能不穿过根结点
     * 思路：
     * 最大值不一定包含根节点，但是一定是：经过一个节点，该节点左右子树的最大深度之和 +1（二叉树的根节点深度为 0）
     * 深度优先。自下而上，使用 DFS，找出所有节点的最大直径，在取出最大值 res.
     * https://leetcode-cn.com/problems/diameter-of-binary-tree/solution/java-shen-du-you-xian-bian-li-dfs-by-sugar-31/
     * <p>
     * 二叉树的直径：二叉树中从一个结点到另一个节点最长的路径，叫做二叉树的直径
     * 采用分治和递归的思想：
     * - 根节点为root的二叉树的直径 = max(root->left的直径，root->right的直径，root->left的最大深度+root->right的最大深度+1)
     * <p>
     * 作者：sammy-4
     * 链接：https://leetcode-cn.com/problems/diameter-of-binary-tree/solution/hot-100-9er-cha-shu-de-zhi-jing-python3-di-gui-ye-/
     *
     * @param root
     * @return
     */
    int resDiameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {

        dfs(root);
        return resDiameter;
    }

    // 函数dfs的作用是：找到以root为根节点的二叉树的最大深度
    private int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = dfs(root.left);
        int rigthDepth = dfs(root.right);
        //   '''每个结点都要去判断左子树+右子树的高度是否大于self.max，更新最大值'''
        resDiameter = Math.max(resDiameter, leftDepth + rigthDepth);
        //返回的root的高度=最大高度+1
        return Math.max(leftDepth, rigthDepth) + 1;
    }

    /**
     * 101. 对称二叉树
     * 递归：
     * 迭代：使用双端队列来存、或者栈
     * 都是O(n)
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        return isSymmetric_recursion(root, root);
    }

    public boolean isSymmetric_recursion(TreeNode t1, TreeNode t2) {
        //都为空则相等
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;
        return (t1.val == t2.val)
                //对称，则是
                && isSymmetric_recursion(t1.right, t2.left)
                && isSymmetric_recursion(t1.left, t2.right);
    }

    public boolean isSymmetric_foreach(TreeNode u, TreeNode v) {
        Stack<TreeNode> q = new Stack<TreeNode>();
        q.add(u);
        q.add(v);
        while (!q.isEmpty()) {
            //取出元素校验
            u = q.pop();
            v = q.pop();
            if (u == null && v == null) {
                continue;
            }
            if ((u == null || v == null) || (u.val != v.val)) {
                return false;
            }
            //添加元素
            q.add(u.left);
            q.add(v.right);

            q.add(u.right);
            q.add(v.left);
        }
        return true;
    }


    /**
     * 104-二叉树的最大深度
     *
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return Math.max(maxDepth(root.right), maxDepth(root.left)) + 1;
    }

    /**
     * @param root
     * @return
     */
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        if (root.left == null && root.right == null) {
            return 1;
        }

        int min_depth = Integer.MAX_VALUE;
        if (root.left != null) {
            min_depth = Math.min(minDepth(root.left), min_depth);
        }
        if (root.right != null) {
            min_depth = Math.min(minDepth(root.right), min_depth);
        }

        return min_depth + 1;
    }

    /**
     * 二叉搜索的最近公共祖先
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor_bts(TreeNode root, TreeNode p, TreeNode q) {
        return  null;
    }

    /**
     * 236. 二叉树的最近公共祖先
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        //如果遍历到叶子节点就返回，或者遍历到目标节点
        if (root == null || root == p || root == q) return root;
        //同一侧就返回不为null的，不同侧就返回父节点
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) {
            return root;
        }
        return left == null ? right : left;
    }

    /**
     * 98. 验证二叉搜索树
     *
     * @param root
     * @return
     */
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, null, null);

    }

    private boolean isValidBST(TreeNode root, Integer min, Integer max) {
        if (root == null) return true;
        if (min != null && root.val <= min) return false;
        if (max != null && root.val >= max) return false;
        //保证左子树的值都小于
        if (!isValidBST(root.left, min, root.val)) return false;
        //保证右子树的值都大于
        if (!isValidBST(root.right, root.val, max)) return false;
        return true;
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