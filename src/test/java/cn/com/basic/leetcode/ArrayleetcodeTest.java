package cn.com.basic.leetcode;

import org.junit.Test;

import java.util.*;

/**
 * Created by zhaijiayi on 2020/5/6.
 */
public class ArrayleetcodeTest {


    @Test
    public void test() throws Exception {
        int[] array = {10, 9, 2, 5, 3, 7, 101, 18};
        lengthOfLIS(array);
    }

    /**
     * 1. 两数之和
     * <p>
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     * <p>
     * 思路：用hash
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> value2index = new HashMap<>();
        //新数组声明
        int[] resArray = new int[2];
        for (int i = 0; i < nums.length; i++) {
            //判断当前这个值和map中是否存在相加=target
            if (value2index.containsKey(target - nums[i])) {
                resArray[0] = value2index.get(target - nums[i]);
                resArray[1] = i;
                return resArray;
            }
            //还没找到，就每次都value存在key中，value记录索引（返回的是索引）
            value2index.put(nums[i], i);
        }
        return resArray;
    }

    /**
     * 15. 三数之和
     * 排个序，然后首尾指针
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList();
        for (int i = 0; i < nums.length; i++) {
            int target = -nums[i];
            int l = i + 1;
            int r = nums.length - 1;
            if (nums[i] > 0)
                break;
            //nums[i] != nums[i - 1]  两个重复则返回的为重复值
            if (i == 0 || nums[i] != nums[i - 1]) {
                while (l < r) {
                    //三数相加为0
                    if (nums[l] + nums[r] == target) {
                        res.add(Arrays.asList(nums[i], nums[l], nums[r]));
                        //相等的指针要跳过
                        while (l < r && nums[l] == nums[l + 1]) l++;//如果两个值相等，左指针向后移，跳过这个
                        while (l < r && nums[r] == nums[r - 1]) r--;//如果两个值相等，右指针向前移，跳过这个
                        //左右指针移动
                        l++;
                        r--;
                        //小于0，移动左指针
                    } else if (nums[l] + nums[r] < target)
                        l++;
                        //大于0，移动右指针
                    else
                        r--;
                }
            }
        }
        return res;
    }

    /**
     * 11. 盛最多水的容器
     * 双指针
     * 容量=两个指针指向的数字中较小值∗指针之间的距离
     */
    @Test
    public int maxArea(int[] height) {
        //遍历数组，记录每次最大的结果
        int res = 0;
        //首位两个指针
        int l = 0;
        int r = height.length - 1;
        while (l < r) {
            int area = Math.min(height[l], height[r]) * (r - l);
            res = Math.max(area, res);
            //如果左右两个值比较，l值<r值，则移动左指针，否则移动右指针
            if (height[l] < height[r]) {
                l++;
            } else {
                r--;
            }
        }
        return res;
    }


    /**
     * 121. 买卖股票的最佳时机
     * 题目：整个过程最多一笔交易
     * todo 原因解释：
     * 假如计划在第 i 天卖出股票，那么最大利润的差值一定是在[0, i-1] 之间选最低点买入；
     * 所以遍历数组，依次求每个卖出时机的的最大差值，再从中取最大值。
     * 时间复杂度：O(n)O(n)，只需要遍历一次。
     * 空间复杂度：O(1)O(1)，只使用了常数个变量。
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        //初始化设置一个最大值
        int subPrice = Integer.MAX_VALUE;
        int resProfit = 0;
        for (int price : prices) {
            if (price < subPrice)
                subPrice = price;
            else if (price - subPrice > resProfit)
                resProfit = price - subPrice;
        }
        return resProfit;
    }

    /**
     * 53. 最大子序和
     * <p>
     * 动态规划：
     * 通俗来讲这个问题就变成了，第i个子组合可以通过第i-1个子组合的最大值和第i个数字获得，如果第i-1个子组合的最大值没法给第i个数字带来正增益，我们就抛弃掉前面的子组合，自己就是最大的了。
     * <p>
     * 如果Max(i−1)>0,Max(i)=Max(i−1)+Nums(i)
     * 如果Max(i−1)<=0,Max(i)=Nums(i)
     * <p>
     * 斐波拉契数列
     * ​
     *
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        if (nums == null) {
            return 0;
        }

        int max = nums[0];    // 全局最大值
        int subMax = nums[0];  // 前一个子组合的最大值
        //sub从0开始，遍历则从1开始
        for (int i = 1; i < nums.length; i++) {
            if (subMax > 0) {
                // 前一个子组合最大值大于0，正增益
                subMax = subMax + nums[i];
            } else {
                // 前一个子组合最大值小于0，抛弃前面的结果
                subMax = nums[i];
            }
            // 计算全局最大值
            max = Math.max(max, subMax);
        }

        return max;
    }


    /**
     * 33. 搜索旋转排序数组
     * <p>
     * 思路：二分法
     *
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        //返回值
        int res = -1;
        //边界场景
        if (nums == null || nums.length == 0) return target;
        if (nums.length == 1) return nums[0] == target ? 0 : res;
        //遍历数组收尾指针
        int left = 0;
        int right = nums.length - 1;
        //中间节点，当找到目标值则返回
        int mid;
        //!!注意等号
        while (left <= right) {
            //中间节点赋值
            mid = (left + right) / 2;
            if (nums[mid] == target) return mid;
            //!!先判断有序，在判断target所在区间
            //left到mid是有序的
            if (nums[left] <= nums[mid]) {
                //target在此区间，！！！注意=
                if (target >= nums[left] && target < nums[mid]) {
                    //注意 -1、+1
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                //target在此区间
                if (target <= nums[right] && target > nums[mid]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }

            }
        }
        return res;

    }


    /**
     * 56. 合并区间
     * 题意：合并重叠区间，没重叠的直接输出
     * todo 二维数组
     * 思路：先排序，再用栈保存，每次取最后一个元素进行比较
     *
     * @param intervals int[n][0]
     * @return
     */
    public int[][] merge(int[][] intervals) {
        int len = intervals.length;
        //边界判断
        if (len < 2) return intervals;
        // ！！！按照起点排序
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));
        //因为我们只关心结果集的最后一个区间 int[2] i长度只会=2   0-左边界、1-右边界
        Stack<int[]> res = new Stack<>();
        //放入第一个值
        res.add(intervals[0]);
        //从第二个值开始遍历，和前一个值比较
        for (int i = 1; i < len; i++) {
            int[] curInterval = intervals[i];
            // 每次新遍历到的列表与当前结果集中的最后一个区间的末尾端点进行比较
            int[] peek = res.peek();
            //如果当前值大于前一个，则没重合 不合并。
            if (curInterval[0] > peek[1]) {
                res.add(curInterval);
            } else {
                //!!! 需要合并，右边界要取最大的值
                peek[1] = Math.max(curInterval[1], peek[1]);
            }
        }
        //!!!方法转换
        return res.toArray(new int[res.size()][]);
    }


    /**
     * todo  322. 零钱兑换  动态规划
     * 凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。
     * 自己的思路：先将数组排序，尽量用大面额的去补充
     * 解题：
     * 方法1-这就是背包问题啊啊啊，动态规划、最优子结构
     * 方法2-广度优先
     *
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange(int[] coins, int amount) {
        return coinChange_1(coins, amount);
    }

    /**
     * 背包问题
     *
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange_1(int[] coins, int amount) {
        //为啥是这是一个二维数组
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;

        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }

        if (dp[amount] == amount + 1) {
            dp[amount] = -1;
        }
        return dp[amount];
    }

    /**
     * todo 广度优先
     * 是一个图结构的
     * 因为是「图」，有回路，所以要设计一个 visited 数组。
     *
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange_2(int[] coins, int amount) {
        return 0;
    }


    /**
     * todo 46. 全排列  没明白
     * 题目：给定一个 没有重复 数字的序列，返回其所有可能的全排列。
     * 思路：回溯算法
     * 其实回溯算法关键在于:不合适就退回上一步
     * 然后通过约束条件, 减少时间复杂度.
     * <p>
     * 参考：https://leetcode-cn.com/problems/permutations/solution/quan-pai-lie-di-gui-hui-su-fa-jian-dan-yi-dong-by-/
     *
     * @param nums
     * @return
     */
    List<List<Integer>> resArray = new LinkedList<>();

    public List<List<Integer>> permute(int[] nums) {
        // 双端列表 记录「路径」
        LinkedList<Integer> track = new LinkedList<>();
        backtrack(nums, track);
        return resArray;
    }

    // 路径：记录在 track 中
// 选择列表：nums 中不存在于 track 的那些元素
// 结束条件：nums 中的元素全都在 track 中出现
    private void backtrack(int[] nums, LinkedList<Integer> track) {
        // 触发结束条件
        if (track.size() == nums.length) {
            resArray.add(new LinkedList(track));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            // 这个数被选过了
            if (track.contains(nums[i]))
                continue;
            // 当前位做选择
            track.add(nums[i]);
            // 继续对下一位选择
            backtrack(nums, track);
            // 递归结束，下一位无法选择递归返回了且没满足结束条件
            // 说明当前位的选择导致了下一位无论怎么选都不能到达结束条件
            // 所以当前位的选择是错误的，撤销当前位选择
            track.removeLast();
        }

    }

    /**
     * 215. 数组中的第K个最大元素 （和703一样）
     * <p>
     * 方法1 - 先排序在返回第k大哥元素   时间复杂度=O(logN)
     * 方法2 - 维护一个小顶堆，保存前k大个元素
     *
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {
        return findKthLargest_method2(nums, k);
    }

    private int findKthLargest_method1(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }

    private int findKthLargest_method2(int[] nums, int k) {
        PriorityQueue<Integer> res = new PriorityQueue(k);
        for (int num : nums) {
            //不到k个值
            if (res.size() < k) {
                res.offer(num);
                //大于第k个
            } else if (num > res.peek()) {
                res.poll();
                res.offer(num);
            }
        }
        return res.peek();
    }

    /**
     * todo 31. 下一个排列  (看不懂)
     * 时间复杂度：O(n)，在最坏的情况下，只需要对整个数组进行两次扫描。
     * <p>
     * 空间复杂度：O(1)，没有使用额外的空间，原地替换足以做到。
     *
     * @param nums
     */
    public void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        while (i >= 0 && nums[i + 1] <= nums[i]) {
            i--;
        }
        if (i >= 0) {
            int j = nums.length - 1;
            while (j >= 0 && nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }
        reverse(nums, i + 1);
    }

    private void reverse(int[] nums, int start) {
        int i = start, j = nums.length - 1;
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 54. 螺旋矩阵
     * 给定一个包含 m x n 个元素的矩阵（m 行, n 列），请按照顺时针螺旋顺序，返回矩阵中的所有元素。
     * <p>
     * 解题：
     *
     * @param matrix
     * @return
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> list = new ArrayList<Integer>();
        if (matrix == null || matrix.length == 0)
            return list;
        int m = matrix.length;
        int n = matrix[0].length;
        int i = 0;

        //需要打印的层数 一层时count=0，统计矩阵从外向内的层数，如果矩阵非空，那么它的层数至少为1层
        int count = (Math.min(m, n) + 1) / 2;
        //从外部向内部遍历，逐层打印数据
        while (i < count) {
            //左->右
            for (int j = i; j < n - i; j++) {
                list.add(matrix[i][j]);
            }
            //上到下
            for (int j = i + 1; j < m - i; j++) {
                list.add(matrix[j][(n - 1) - i]);
            }
            //右到左
            for (int j = (n - 1) - (i + 1); j >= i && (m - 1 - i != i); j--) {
                list.add(matrix[(m - 1) - i][j]);
            }
            //下到上
            for (int j = (m - 1) - (i + 1); j >= i + 1 && (n - 1 - i) != i; j--) {
                list.add(matrix[j][i]);
            }
            i++;
        }
        return list;

    }

    /**
     * todo 560. 和为K的子数组 （没看懂啥叫前缀和）
     * 给定一个整数数组和一个整数 k，你需要找到该数组中和为 k 的连续的子数组的个数。
     * 解题思路：
     * 方法1- 前缀和、前缀和优化
     * 时间复杂度：O(N^2)
     * 空间复杂度：O(N)
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraySum_1(int[] nums, int k) {
        int len = nums.length;
        // 计算前缀和数组
        int[] preSum = new int[len + 1];
        preSum[0] = 0;
        for (int i = 0; i < len; i++) {
            preSum[i + 1] = preSum[i] + nums[i];
        }

        int count = 0;
        for (int left = 0; left < len; left++) {
            for (int right = left; right < len; right++) {
                // 区间和 [left..right]，注意下标偏移
                if (preSum[right + 1] - preSum[left] == k) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 方法2 -前缀和 + 哈希表优化
     * * 时间复杂度：O(N)
     * * 空间复杂度：O(N)
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraySum_2(int[] nums, int k) {
        int sum = 0;
        int n = nums.length;
        int[] preixSum = new int[n];
        preixSum[0] = nums[0];
        for (int i = 1; i < n; i++) {
            preixSum[i] = preixSum[i - 1] + nums[i];
        }

        HashMap<Integer, Integer> hashMap = new HashMap<>();
        hashMap.put(0, 1);//当i-1<0时，前缀和数组越界，(0,1)表示此时减去的是0
        for (int preixsum : preixSum) {
            if (hashMap.containsKey(preixsum - k)) {
                sum += hashMap.get(preixsum - k);
            }
            hashMap.put(preixsum, hashMap.getOrDefault(preixsum, 0) + 1);
        }
        return sum;
    }


    /**
     * todo 300. 最长上升子序列
     * 暴利求解： O(n2) - 动态规划
     * 优化算法：O(n log n) - 动态规划 + 二分查找
     *
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        int res = 0;
        //这个方法啥意思，将dp数组内的每一个索引位置都 设置成1，但是为啥都是1呢？
        Arrays.fill(dp, 1);
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                //todo 这块为啥啊啊啊
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            res = Math.max(res, dp[i]);
        }
        return res;

    }

}