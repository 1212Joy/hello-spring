package cn.com.basic.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaijiayi on 2020/5/6.
 */
public class IntLeetcodeTest {


    @Test
    public void test() throws Exception {

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
     * todo 70. 爬楼梯（没看懂）
     * <p>
     * 通项公式、动态规划
     *
     * @param n
     * @return
     */
    public int climbStairs(int n) {
        return 0;
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

}