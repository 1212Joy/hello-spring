package cn.com.basic.leetcode;

import org.junit.Test;

import java.util.*;

/**
 * Created by zhaijiayi on 2020/5/6.
 */
public class IntLeetcodeTest {


    @Test
    public void test() throws Exception {
        int[][] aaa = {{2}, {3, 4}, {6, 5, 7}, {4, 1, 8, 3}};
        // System.out.println(minimumTotal(aaa));

    }

    /**
     * todo  7. 整数反转
     * 题意：给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。（题没看懂）
     * 假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−2^31,  2^31 − 1]。
     * <p>
     * 反转后有可能溢出int最大值
     * <p>
     * 参考：https://leetcode-cn.com/problems/reverse-integer/solution/tu-wen-xiang-jie-javadai-ma-de-2chong-shi-xian-fan/
     *
     * @param x
     * @return
     */
    public int reverse(int x) {
        int res = 0;
        while (x != 0) {
            //取余数
            int t = x % 10;
            //反转后的值
            int newRes = res * 10 + t;
            //todo 啥意思 如果数字溢出，直接返回0
            if ((newRes - t) / 10 != res) return 0;
            res = newRes;

            //取整
            x = x / 10;
        }

        return res;
    }


    /**
     * 22-有效括号生成  指定数量
     * 递归
     *
     * @param n
     * @return
     */
    List<String> result;
    int n;

    public List<String> generateParenthesis(int n) {
        this.result = new ArrayList<>();
        this.n = n;
        //调用递归方法
        generate(0, 0, "");
        return result;
    }

    void generate(int left, int right, String value) {
        //括号数等于n，则退出条件
        if (left == n && right == n) {
            result.add(value);
            return;
        }
        //合法括号是先左在右，先添加左括号
        if (left < n) {
            generate(left + 1, right, value + "(");
        }
        //如果左括号比右括号多，则需要添加右括号，
        if (left > right && right < n) {
            generate(left, right + 1, value + ")");
        }

    }

    /**
     * 50. Pow(x, n)
     * <p>
     * 即计算 x 的 n 次幂函数
     * <p>
     * 分治。
     *
     * @param x
     * @param n
     * @return
     */
    public double myPow(double x, int n) {
        if (n == 0 || x == 1) {
            return 1;
        }
        //n小于0
        if (n < 0) return 1 / myPow(x, -n);
        //奇数个
        if (n % 2 == 1) {
            return x * myPow(x, n - 1);
            //偶数个
        } else {
            return myPow(x * x, n / 2);
        }

    }

    /**
     * 69. x 的平方根
     * 二分法
     * <p>
     * 二分查找的下界为 0，上界可以粗略地设定为 x。在二分查找的每一步中，我们只需要比较中间元素 mid 的平方与 x 的大小关系，并通过比较的结果调整上下界的范围。
     * 由于我们所有的运算都是整数运算，不会存在误差，因此在得到最终的答案 ans 后，也就不需要再去尝试 ans+1 了。
     *
     * @param x
     * @return
     */
    public int mySqrt(int x) {
        //l,r为上下界
        int l = 0, r = x, ans = -1;
        while (l <= r) {
            //为啥是r-l
            int mid = l + (r - l) / 2;
            //用long防止int越界
            if ((long) mid * mid <= x) {
                ans = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return ans;
    }

    /**
     * 239. 滑动窗口最大值
     *
     * @param nums
     * @param k
     * @return
     */
    //滑动窗口
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length == 0 || nums.length == 1) {
            return nums;
        }
        //小顶堆，
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


    /**
     * 169. 多数元素
     * <p>
     * 多数元素是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。
     * <p>
     * 投票方法
     *
     * @param nums
     * @return
     */
    public int majorityElementVote(int[] nums) {
        int chosen = nums[0];
        int count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                count++;
            } else count--;
            //代表抵消了了，重选选现在这个对象
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

}