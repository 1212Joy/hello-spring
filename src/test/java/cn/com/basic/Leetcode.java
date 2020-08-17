package cn.com.basic;

import org.junit.Test;
import sun.text.normalizer.Trie;

import java.util.*;

public class Leetcode {
    @Test
    public void randomDouble() throws Exception {

        List<String> res = generateParenthesis(3);
        System.out.println(res);
    }

    List<String> result;
    int n;

    //22
    public List<String> generateParenthesis(int n) {
        this.result = new ArrayList<>();
        this.n = n;
        generate(0, 0, "");
        return result;
    }

    void generate(int left, int right, String value) {
        //退出条件
        if (left == n && right == n) {
            result.add(value);
            return;
        }
        //合法括号是先左在右
        if (left < n) {
            generate(left + 1, right, value + "(");
        }
        if (left > right && right < n) {
            generate(left, right + 1, value + ")");
        }

    }

    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        int right = minDepth(root.right);
        int left = minDepth(root.left);
        return right == 0 || left == 0 ? right + left + 1 : Math.min(right, left) + 1;
    }

    public int maxDepthBFS(TreeNode root) {
        int depth = 0;
        if (root == null) return depth;
        LinkedList<TreeNode> queue = new LinkedList();
        queue.add(root);
        while (!queue.isEmpty()) {
            int currentSize = queue.size();
            for (int i = 0; i < currentSize; i++) {
                TreeNode current = queue.poll();
                if (current.right != null) {
                    queue.add(current.right);
                }
                if (current.left != null) {
                    queue.add(current.left);
                }
            }
            depth++;
        }
        return depth - 1;
    }

    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return Math.max(maxDepth(root.right), maxDepth(root.left)) + 1;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        //LinkedList是一种双端队列,和Deque有啥区别
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        //初始从左到右
        boolean left2right = true;
        while (!queue.isEmpty()) {
            LinkedList<Integer> currentLevel = new LinkedList<>();
            Integer currentLevelSize = queue.size();
            for (int i = 0; i < currentLevelSize; i++) {
                //取出第一层元素，将左右节点放到当前队列中
                TreeNode current = queue.pollFirst();
                //如果是从左到右，就放到对头
                if (left2right) {
                    currentLevel.addLast(current.val);
                    //从右到左，放到队尾
                } else {
                    currentLevel.addFirst(current.val);
                }

                //每一层从左到右方放元素
                if (current.left != null) {
                    queue.add(current.left);
                }
                if (current.right != null) {
                    queue.add(current.right);
                }

            }
            //每遍历完一层更换一层
            left2right = !left2right;
            res.add(currentLevel);

        }
        return res;
    }

    //102-广度优先
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
                //当前是奇数行，下一行是偶数行从左到右
                if (currentLevelSize % 2 == 1) {
                    if (current.right != null) {
                        queue.add(current.right);
                    }
                    if (current.left != null) {
                        queue.add(current.left);
                    }
                } else {
                    if (current.left != null) {
                        queue.add(current.left);
                    }
                    if (current.right != null) {
                        queue.add(current.right);
                    }
                }


            }
            res.add(currentLevel);
        }

        return res;
    }


    public int majorityElementVote(int[] nums) {
        int chosen = nums[0];
        int count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                count++;
            } else count--;
            if (count == 0) {
                chosen = nums[i];
            }

        }
        return chosen;
    }

    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }

    public double myPow(double x, int n) {
        if (n == 0 || x == 1) {
            return 1;
        }
        //n小于0
        if (n < 0) return 1 / myPow(x, -n);
        //奇数个
        if (n % 2 == 1) return x * myPow(x, n - 1);
        return myPow(x * x, n / 2);
    }

    public List<List<Integer>> threeSumBySort(int[] nums) {
        List<List<Integer>> threeSumArray = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            int target = -nums[i];
            int l = i + 1;
            int r = nums.length - 1;
            //三个值都大于0则不可能相加为0
            if (nums[i] > 0)
                break;
            if (i == 0 || nums[i] != nums[i - 1]) {
                while (l < r) {
                    if (nums[l] + nums[r] == target) {
                        threeSumArray.add(Arrays.asList(nums[i], nums[l], nums[r]));
                        while (l < r && nums[l] == nums[l + 1]) l++;
                        while (l < r && nums[r] == nums[r - 1]) r--;
                        l++;
                        r--;
                    } else if (nums[l] + nums[r] < target)
                        l++;
                    else
                        r--;
                }
            }
        }

        return threeSumArray;
    }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> threeSumArray = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int[] twoSum = twoSum(nums, i, -nums[i]);
            if (twoSum.length != 0) {
                List<Integer> temp = new ArrayList<>();
                temp.add(nums[i]);
                temp.add(nums[twoSum[0]]);
                temp.add(nums[twoSum[1]]);
                threeSumArray.add(temp);
            }
        }
        return threeSumArray;
    }

    public int[] twoSum(int[] nums, int startIndex, int target) {
        Map<Integer, Integer> value2index = new HashMap<>();
        int[] resArray = new int[2];
        for (int i = startIndex; i < nums.length; i++) {
            value2index.put(target - nums[i], i);
            if (value2index.containsKey(nums[i])) {
                resArray[0] = value2index.get(nums[i]);
                resArray[1] = i;
                return resArray;
            }
        }
        return new int[0];
    }

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> value2index = new HashMap<>();
        int[] resArray = new int[2];
        for (int i = 0; i < nums.length; i++) {
            value2index.put(target - nums[i], i);
            if (value2index.containsKey(nums[i])) {
                resArray[0] = value2index.get(nums[i]);
                resArray[1] = i;
                return resArray;
            }
        }
        return new int[0];
    }

    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int length = t.length();
        Map<Character, Integer> char2count = new HashMap();
        for (int i = 0; i < length; i++) {
            char2count.put(s.charAt(i), char2count.getOrDefault(s.charAt(i), 0) + 1);
            char2count.put(t.charAt(i), char2count.getOrDefault(t.charAt(i), 0) - 1);
        }
        Iterator<Integer> val = char2count.values().iterator();
        while (val.hasNext()) {
            if (val.next() != 0) {
                return false;
            }
        }

        return true;
    }

    //滑动窗口-双端队列
    public int[] maxSlidingWindowByDeque(int[] nums, int k) {
        if (nums.length == 0 || nums.length == 1) {
            return nums;
        }
        int[] maxArray = new int[nums.length - k + 1];
        LinkedList<Integer> windowDeque = new LinkedList();
        for (int i = 0; i < nums.length; i++) {
            // 保证从大到小 如果前面数小则需要依次弹出，直至满足要求.弹出最后末尾元素
            while (!windowDeque.isEmpty() && nums[windowDeque.peekLast()] <= nums[i]) {
                windowDeque.pollLast();
            }
            // 添加当前值对应的数组下标
            windowDeque.addLast(i);
            // 判断当前队列中队首的值是否有效，小于右边界则将当前顶端元素移除
            if (windowDeque.peek() <= i - k) {
                windowDeque.poll();
            }
            // 当窗口长度为k时 保存当前窗口中最大值
            if (i + 1 >= k) {
                maxArray[i + 1 - k] = nums[windowDeque.peek()];
            }


        }
        return maxArray;
    }


    //滑动窗口
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length == 0 || nums.length == 1) {
            return nums;
        }
        PriorityQueue<Integer> priorityQueue = new PriorityQueue(k, Comparator.reverseOrder());
        int[] maxArray = new int[nums.length - k + 1];
        int index = 0;
        for (int i = 0; i < nums.length; i++) {

            if (priorityQueue.isEmpty() || i < k - 1) {
                priorityQueue.offer(nums[i]);
                continue;
            }
            //当队列有k个值得时候就可以开始往返回数组填充值了
            else if (nums[i] > priorityQueue.peek()) {
                //大于队列里的最小值需要替换，先移除再添加
                priorityQueue.poll();
                priorityQueue.offer(nums[i]);
            }
            maxArray[index] = priorityQueue.peek();
            index++;
        }
        return maxArray;
    }

}


//
class KthLargest {
    private int k;
    private PriorityQueue<Integer> priorityQueue;

    public KthLargest(int k, int[] nums) {
        this.k = k;
        this.priorityQueue = new PriorityQueue(k);
        for (int i = 0; i < nums.length; i++) {
            add(nums[i]);
        }
    }

    public int add(int val) {
        if (priorityQueue.size() < k) {
            priorityQueue.offer(val);
            return val;
        }
        priorityQueue.comparator();
        if (val <= priorityQueue.peek()) {
            return priorityQueue.peek();
        }
        priorityQueue.poll();
        priorityQueue.add(val);
        priorityQueue.comparator();
        return priorityQueue.peek();
    }

}