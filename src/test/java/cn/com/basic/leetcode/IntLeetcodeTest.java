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
     * 50. Pow(x, n)
     * <p>
     * 即计算 x 的 n 次幂函数。
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

}