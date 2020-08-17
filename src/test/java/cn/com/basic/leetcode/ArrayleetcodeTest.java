package cn.com.basic.leetcode;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;

/**
 * Created by zhaijiayi on 2020/5/6.
 */
public class ArrayleetcodeTest {


    @Test
    public void test() throws Exception {

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
     * @param intervals  int[n][0]
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