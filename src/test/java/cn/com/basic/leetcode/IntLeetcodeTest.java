package cn.com.basic.leetcode;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhaijiayi on 2020/5/6.
 */
public class IntLeetcodeTest {


    @Test
    public void test() throws Exception {
        int[][] aaa = {{2}, {3, 4}, {6, 5, 7}, {4, 1, 8, 3}};
        System.out.println(minimumTotal(aaa));

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
     * 70. 爬楼梯（没看懂）
     * 每次你可以爬 1 或 2 个台阶
     * <p>
     * 通项公式、动态规划
     * 斐波拉契数列：爬到n层等于爬到前两个楼梯和f（n）=f（n-1）+f（n-2）
     * //自下向上递推，0，1，2这三种情况是固定的
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
            stair2num[i] = stair2num[i - 1] + stair2num[i - 2];
        }
        return stair2num[n - 1] + stair2num[n - 2];
    }

    /**
     * 方法1 -动态规划
     * f(x)=f(x−1)+f(x−2)
     * 时间复杂度：循环执行 nn 次，每次花费常数的时间代价，故渐进时间复杂度为 O(n)O(n)。
     * 空间复杂度：这里只用了常数个变量作为辅助空间，故渐进空间复杂度为 O(1)O(1)。
     *
     * @param n
     * @return
     */
    private int climbStairs_1(int n) {
        int p = 0;
        int q = 0;
        int r = 1;
        //至少从1楼开始
        for (int i = 1; i <= n; i++) {
            p = q;
            q = r;
            r = p + q;
        }
        return r;

    }

    /**
     * 方法2 -通项公式（斐波拉契数列）
     * 时间复杂度：O(logn)，pow 方法将会用去 O(logn) 的时间。
     * 空间复杂度：O(1)。
     *
     * @param n
     * @return
     */
    public int climbStairs_2(int n) {
        double sqrt5 = Math.sqrt(5);
        double fibn = Math.pow((1 + sqrt5) / 2, n + 1) - Math.pow((1 - sqrt5) / 2, n + 1);
        return (int) (fibn / sqrt5);
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
     * 9. 回文数
     * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
     * 1.转换成String，然后两个指针解决
     * 2.数学法 - O(\log n)
     *
     * @param x
     * @return
     */
    public boolean isPalindrome_1(int x) {
        if (x < 0) return false;
        if (x == 0) return true;
        //
        String target = String.valueOf(x);
        char[] tarChars = target.toCharArray();
        int left = 0;
        int right = tarChars.length - 1;
        while (left <= right) {
            if (tarChars[left] != tarChars[right]) {
                return false;
            }
            left++;
            right--;

        }
        return true;
    }

    /**
     * todo 看不懂
     *
     * @param x
     * @return
     */
    public boolean isPalindrome_2(int x) {
        // 特殊情况：
        // 如上所述，当 x < 0 时，x 不是回文数。
        // 同样地，如果数字的最后一位是 0，为了使该数字为回文，
        // 则其第一位数字也应该是 0
        // 只有 0 满足这一属性
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        int revertedNumber = 0;
        while (x > revertedNumber) {
            revertedNumber = revertedNumber * 10 + x % 10;
            x /= 10;
        }

        // 当数字长度为奇数时，我们可以通过 revertedNumber/10 去除处于中位的数字。
        // 例如，当输入为 12321 时，在 while 循环的末尾我们可以得到 x = 12，revertedNumber = 123，
        // 由于处于中位的数字不影响回文（它总是与自己相等），所以我们可以简单地将其去除。
        return x == revertedNumber || x == revertedNumber / 10;
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
        int[] dp =  triangle[triangle.length - 1];
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

    public int[][] minimumTotal(List<List<Integer>> triangle) {
        return (int[][])triangle.toArray(new int[0][]);
    }


}