package cn.com.basic.leetcode;

import org.junit.Test;

/**
 * Created by zhaijiayi on 2020/5/6.
 */
public class PalindromeTest {


    @Test
    public void test() throws Exception {

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
     * 5. 最长回文子串
     * 分析：
     * a.查询最长的回文子串，涉及到子操作，则可以想到动态规划
     * b.啥叫回文？轴对称字符串，比如：bb、aba、等
     * <p>
     * 实现：
     * a.回溯算法：暴利匹配、全部列举  时间复杂度：O(n^2)
     * b.动态规划：
     * c.中心扩散
     *
     * @param s
     * @return
     */
//    public String longestPalindrome_methodA(String s) {
//
//        int length = s.length();
//        //边界判断
//        if (length < 2) return s;
//        //记录全局最大长度，因为s长度大于1所以至少为1
//        int maxLength = 1;
//        //记录左指针
//        int left = 0;
//        // s.charAt(i) 每次都会检查数组下标越界，因此先转换成字符数组
//        char[] charArray = s.toCharArray();
//
//        // 枚举所有长度大于 1 的子串 charArray[i..j],通过两层遍历
//        for (int i = 0; i < length - 1; i++) {
//            for (int j = i + 1; j < length; j++) {
//                //剪枝，只判断大于当前最大值的string
//                if (j - i + 1 > maxLength && validPalindromic(charArray, i, j)) {
//                    maxLength = j - i + 1;
//                    left = i;
//                }
//            }
//        }
//        return s.substring(left, left + maxLength);
//
//    }


    /**
     * 验证时否为回文
     *
     * @param charArray
     * @param left
     * @param right
     * @return
     */
    private boolean validPalindromic(char[] charArray, int left, int right) {
        while (left < right) {
            if (charArray[left] != charArray[right]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    /**
     * 动态规划
     * 思路：
     * a.对于一个子串而言，如果它是回文串，并且长度大于 2，那么将它首尾的两个字母去除之后，它仍然是个回文串。例如对于字符串 \textrm{``ababa''}“ababa”，如果我们已经知道 \textrm{``bab''}“bab” 是回文串，那么 \textrm{``ababa''}“ababa” 一定是回文串
     * 状态定义：P(i,j) 代表i到j是否是回文，S i标识i这个字符
     * 状态分析
     * 边界判断：
     * 长度<=2. =1:true   =2时：相等就是
     * <p>
     * 方程定义：P(i,j)=P(i+1,j−1)∧(S i ==S j)
     *
     * @param s
     * @return
     */
    public String longestPalindrome_methodB(String s) {
        // 边界判断
        int len = s.length();
        //==1的时候，是回文
        if (len < 2) {
            return s;
        }

        int maxLen = 1;
        int begin = 0;

        // dp[i][j] 表示 s[i, j] 是否是回文串
        boolean[][] dp = new boolean[len][len];
        char[] charArray = s.toCharArray();
        //为啥都先设置为true
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }
        for (int j = 1; j < len; j++) {
            for (int i = 0; i < j; i++) {
                //最外层两个值是否一致
                if (charArray[i] != charArray[j]) {
                    dp[i][j] = false;
                } else {
                    if (j - i < 3) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }

                // 只要 dp[i][j] == true 成立，就表示子串 s[i..j] 是回文，此时记录回文长度和起始位置
                if (dp[i][j] && j - i + 1 > maxLen) {
                    maxLen = j - i + 1;
                    begin = i;
                }
            }
        }
        return s.substring(begin, begin + maxLen);
    }

    /**
     * todo 647. 列举全部回文子串
     * <p>
     * 方法2-动态规划
     * 计算这个字符串中有多少个回文子串。
     *
     * @param s
     * @return
     */
    public int countSubstrings(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int result = s.length();
        for (int i = 0; i < n; i++) dp[i][i] = true;
        //右指针
        for (int i = n - 1; i >= 0; i--) {
            //做指针
            for (int j = i + 1; j < n; j++) {
                //左右相同
                if (s.charAt(i) == s.charAt(j)) {
                    //两个相遇就代表是一个字符，
                    if (j - i == 1) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                    //左右不同
                } else {
                    dp[i][j] = false;
                }
                if (dp[i][j]) {
                    result++;
                }
            }
        }
        return result;
    }


}