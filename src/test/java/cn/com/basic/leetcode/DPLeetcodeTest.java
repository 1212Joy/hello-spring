package cn.com.basic.leetcode;

import java.util.*;

public class DPLeetcodeTest {
    /**
     * 11. 盛最多水的容器
     * 双指针
     * 容量=两个指针指向的数字中较小值∗指针之间的距离
     */
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

    public List<Integer> maxSubArray_printSub(int[] nums) {
        List<Integer> resList = new ArrayList<>();
        if (nums == null) {
            return resList;
        }

        int max = nums[0];    // 全局最大值
        int subMax = nums[0];  // 前一个子组合的最大值
        //sub从0开始，遍历则从1开始
        for (int i = 1; i < nums.length; i++) {
            if (subMax > 0) {
                // 前一个子组合最大值大于0，正增益
                subMax = subMax + nums[i];
                resList.add(nums[i]);
            } else {
                // 前一个子组合最大值小于0，抛弃前面的结果
                subMax = nums[i];
                resList.clear();
                resList.add(nums[i]);
            }
            // 计算全局最大值
            max = Math.max(max, subMax);
        }

        return resList;
    }

    /**
     * 152. 乘积最大子数组
     * 题目:连续的子数组
     * 思路：动态递推，2种case，所以构建一个二维数组 表示
     * case1-正数
     * case2-负数
     * <p>
     * 问题：0的场景在哪判断
     *
     * @param nums
     * @return
     */
    public int maxProduct(int[] nums) {
        int max = nums[0];
        if (nums.length == 1) return max;
        //[]代表nums的位置 []代表正负，只有2位，0代表正数、1代表负数
        int[][] dp = new int[nums.length][2];
        //初始化第一个数据
        dp[0][0] = max;
        dp[0][1] = max;
        //递推由第一个值开始
        for (int i = 1; i < nums.length; i++) {
            //正数，保存最大的
            dp[i][0] = nums[i] >= 0 ? (dp[i - 1][0] * nums[i]) : (dp[i - 1][1] * nums[i]);
            dp[i][0] = Math.max(dp[i][0], nums[i]);
            //负数，保存最小的
            dp[i][1] = nums[i] >= 0 ? (dp[i - 1][1] * nums[i]) : (dp[i - 1][0] * nums[i]);
            dp[i][1] = Math.min(dp[i][1], nums[i]);
            //最大值
            max = Math.max(max, dp[i][0]);
        }
        return max;
    }

    /**
     * 322. 零钱兑换  动态规划
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
        //dp[i] 表示i面值最少，需要多少个硬币
        int[] dp = new int[amount + 1];
        //将dp里面每一个值都赋值成比amount大的值,若最后res比amount大的话，则代表没有满足条件的返回-1
        Arrays.fill(dp, amount + 1);
        //初始化第一个值
        dp[0] = 0;
        //开始遍历,外层从1开始
        for (int i = 1; i <= amount; i++) {
            //遍历零钱的面值
            for (int j = 0; j < coins.length; j++) {
                //当前零钱面值比
                if (coins[j] <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }


        return dp[amount] > amount ? -1 : dp[amount];
    }

    /**
     * 560 和为K的子数组
     * 给定一个整数数组和一个整数 k，你需要找到该数组中和为 k 的连续的子数组的个数。
     * 解题思路：
     * 求出每一个数组元素的的前缀和，
     * 方法1- 前缀和、前缀和优化
     * 时间复杂度：O(N^2)
     * 空间复杂度：O(N)
     *
     * @param nums
     * @param k
     * @return
     */
    public int subarraySum(int[] nums, int k) {
        int len = nums.length;
        // 计算前缀和数组
        int[] preSum = new int[len + 1];
        preSum[0] = 0;
        //当前元素子序和=前一个元素子序和+当前值
        for (int i = 0; i < len; i++) {
            preSum[i + 1] = preSum[i] + nums[i];
        }
        //求数组两数和=k
        int count = 0;
        for (int left = 0; left < len; left++) {
            for (int right = left; right < len; right++) {
                // 区间和 [left..right]，注意下标偏移，这块为啥是相减
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
     * 325. 和等于 k 的最长子数组长度
     * <p>
     * 返回最大的数组长度，且时间复杂度为O（n）
     * <p>
     * 分析：O（n）的话，则用map进行存储
     *
     * @param nums
     * @param k
     * @return
     */

    public int maxSubArrayLen(int[] nums, int k) {
        int n = nums.length;
        // 值和下标
        HashMap<Integer, Integer> sumValue2Index = new HashMap<>();
        sumValue2Index.put(0, -1);

        int res = 0;
        //前缀和，preixSum[i] 代表前i+1个值得和是多少
        int[] preixSum = new int[n];
        for (int i = 0; i < n; i++) {
            preixSum[i] = i == 0 ? nums[0] : preixSum[i - 1] + nums[i];
            //用map存，存在相加等于k的前缀和
            if (sumValue2Index.containsKey(preixSum[i] - k)) {
                Integer idx = sumValue2Index.get(preixSum[i] - k);
                //最长的保存
                res = Math.max(res, i - idx);
            }
            // 这里放的时候要注意，如果两个地方的前缀和一样，也就是之前放过，那我们就不要再放进去了，因为我们要最长的子数组
            if (!sumValue2Index.containsKey(preixSum[i])) {
                sumValue2Index.put(preixSum[i], i);
            }
        }
        return res;
    }

    /**
     * 198. 打家劫舍
     *
     * 如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
     *
     * 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
     *
     * <p>
     * 1.滚动数组
     * 2.动态规划
     *
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int length = nums.length;
        if (length == 1) {
            return nums[0];
        }
        int first = nums[0], second = Math.max(nums[0], nums[1]);
        for (int i = 2; i < length; i++) {
            int temp = second;
            second = Math.max(first + nums[i], second);
            first = temp;
        }
        return second;

    }


    /**
     * 300. 最长上升子序列
     * 动态规划： O(n2) - 动态规划
     * 优化算法：O(n log n) - 动态规划 + 二分查找（维护一个排序数组记录，每当比当前最大元素大时，就替换掉一个元素）
     *
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        int res = 0;
        //将dp数组内的每一个索引位置都 设置成1，因为最少是1
        Arrays.fill(dp, 1);
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                //选出第i个元素最长上升子序列。当前元素值小于前一个值，则当前最长=前一个最长+1
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            res = Math.max(res, dp[i]);
        }
        return res;

    }


    /**
     * 120. 三角形最小路径和
     * 动态规划，自底向上计算，算到顶点结束
     * 每一个层=
     *
     * @param triangle
     * @return
     */
    public int minimumTotal(int[][] triangle) {
        if (triangle == null || triangle.length == 0) return 0;
        //只用了一维数组，因为从低向向上，底层数量最多，且只会使用一次
        int[] dp = triangle[triangle.length - 1];
        //最底层是没有节点可去的，所以遍历从倒数第二层开始一直遍历到0
        for (int i = triangle.length - 2; i >= 0; i--) {
            //开始遍历每一个节点
            for (int j = 0; j < triangle[i].length; j++) {
                //当前节点的最小路径公式=下一层最小值+当前值，在 dp[j]被重新赋值前都是下一层的对应位置的值。j只会和j和j+1产生关系
                dp[j] = triangle[i][j] + Math.min(dp[j], dp[j + 1]);
            }
        }
        return dp[0];
    }

    /**
     * 70. 爬楼梯
     * 每次你可以爬 1 或 2 个台阶
     * <p>
     * 通项公式（自底向上推）、动态规划
     * 斐波拉契数列：爬到n层等于爬到前两个楼梯和f（n）=f（n-1）+f（n-2）
     *
     * @param n
     * @return
     */
    public int climbStairs(int n) {
        if (n == 0 || n == 1) return n;
        int[] stair2num = new int[n];
        //1层只有2种
        stair2num[0] = 1;
        stair2num[1] = 1;
        //大于两层就等于斐波拉契数列，循环从2开始
        for (int i = 2; i < n; i++) {
            //因为要么一次爬两层或者一次爬一层
            stair2num[i] = stair2num[i - 1] + stair2num[i - 2];
        }
        return stair2num[n - 1] + stair2num[n - 2];
    }


    /**
     * 221-最大正方形
     * 思路：动态规划
     * 疑问：正方形怎么保证.,
     * <p>
     * 状态定义：用 dp(i, j)dp(i,j) 表示以 (i, j)(i,j) 为右下角，且只包含 1 的正方形的边长最大值
     * 方程定义：dp(i,j)=min(dp(i−1,j),dp(i−1,j−1),dp(i,j−1))+1  该位置的值是 1，则 dp(i,j) 的值由其上方、左方和左上方的三个相邻位置的 dp值决定
     * 边界：
     * 如果 i 和 j 中至少有一个为 0，则以位置 (i, j) 为右下角的最大正方形的边长只能是 1，因此 dp(i, j) = 1。
     * <p>
     * 时间复杂度：O（m*n）
     *
     * @param matrix
     * @return
     */
    public int maximalSquare(char[][] matrix) {
        //全局维护
        int maxSide = 0;
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return maxSide;
        }
        int rows = matrix.length, columns = matrix[0].length;
        int[][] dp = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                //可以当正方形的边
                if (matrix[i][j] == '1') {
                    //边界。i 和 j 中至少有一个为 0，则以位置 (i, j) 为右下角的最大正方形的边长只能是 1，因此 dp(i, j) = 1
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                        //内部
                    } else {
                        //变长+1，取三者最小值
                        dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                    }
                    //全局维护
                    maxSide = Math.max(maxSide, dp[i][j]);
                }
            }
        }
        int maxSquare = maxSide * maxSide;
        return maxSquare;

    }

    /**
     * 1277. 统计全为 1 的正方形子矩阵
     * <p>
     * 重写了matrix，但由于不会再重复了所以也ok
     *
     * @param matrix
     * @return
     */
    public int countSquares(int[][] matrix) {
        int r = matrix.length;
        int c = matrix[0].length;
        int res = 0;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (matrix[i][j] == 1) {
                    if (i == 0 || j == 0) {
                        res += matrix[i][j];
                        continue;
                    } else {
                        //为啥是min的。三个值得最小值，如果有0就代表不是1了
                        matrix[i][j] = Math.min(matrix[i - 1][j], Math.min(matrix[i][j - 1], matrix[i - 1][j - 1])) + 1;
                    }
                }
                res += matrix[i][j];
            }
        }
        return res;
    }

    /**
     * 64. 最小路径和
     * <p>
     * 每次只能向下或者向右移动一步
     *
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        int rows = grid.length, columns = grid[0].length;
        //代表从左上角网格走到当前的最小路径
        int[][] dp = new int[rows][columns];
        //左上角
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (i == 0 && j == 0) {
                    //0的时候就等于它本身
                    dp[i][j] = grid[i][j];
                    //由一步走过来
                } else if (i == 0) {
                    dp[i][j] = dp[i][j - 1] + grid[i][j];
                } else if (j == 0) {
                    dp[i][j] = dp[i - 1][j] + grid[i][j];
                    //由两步走过来，取两者的最小值
                } else {
                    dp[i][j] = Math.min(dp[i][j - 1], dp[i - 1][j]) + grid[i][j];
                }

            }
        }
        //返回右下角的值
        return dp[rows - 1][columns - 1];

    }


    /**
     * todo 动态方程没看懂  375. 猜数字大小 II
     * <p>
     * 给定 n ≥ 1，计算你至少需要拥有多少现金才能确保你能赢得这个游戏。
     * <p>
     * 至少
     * <p>
     * 方法1：动态规划  - n^3
     *
     * @param n
     * @return
     */
    public int getMoneyAmount_1(int n) {
        //从i到j猜数字，猜出任意最小值
        int[][] dp = new int[n + 1][n + 1];
        //n的个数，
        for (int len = 2; len <= n; len++) {
            //从1
            for (int i = 1; i + len - 1 <= n; i++) {
                int j = i + len - 1;
                //附一个最大值
                dp[i][j] = Integer.MAX_VALUE;
                //从i到j
                for (int k = i; k <= j; k++) {
                    // todo 没看懂
                    // 如何写出相应的代码更新dp矩阵, 递推式dp[i][j] = max(max(dp[i][x-1], dp[x+1][j]) + x), x~[i:j], 可以画出矩阵图协助理解, 可以发现
                    //        dp[i][x-1]始终在dp[i][j]的左部, dp[x+1][j]始终在dp[i][j]的下部, 所以更新dp矩阵时i的次序应当遵循bottom到top的规则, j则相反, 由于
                    //        i肯定小于等于j, 所以我们只需要遍历更新矩阵的一半即可(下半矩阵)
                    dp[i][j] = Math.min(dp[i][j], k +
                            Math.max(k <= 1 ? 0 : dp[i][k - 1], k + 1 > j ? 0 : dp[k + 1][j]));
                }
            }
        }
        //返回从1到n的最小结果
        return dp[1][n];
    }

    /**
     * 374. 猜数字大小
     * 二分法返回结果
     *
     * @param n
     * @return
     */
    public int guessNumber(int n) {
//        int low = 1;
//        int high = n;
//        while (low <= high) {
//            int mid = low + (high - low) / 2;
//            int res = guess(mid);
//            if (res == 0)
//                return mid;
//            else if (res < 0)
//                high = mid - 1;
//            else
//                low = mid + 1;
//        }
        return -1;
    }

}
