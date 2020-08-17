package cn.com.basic.leetcode;

import org.junit.Test;

import java.util.*;

/**
 * Created by zhaijiayi on 2020/5/6.
 */
public class ArrayleetcodeTest {


    @Test
    public void test() throws Exception {

    }

    /**
     * 1. 两数之和
     * <p>
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
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
     * 排个序，然后首位指针
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
}