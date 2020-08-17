package cn.com.basic.leetcode;

import org.junit.Test;

/**
 * Created by zhaijiayi on 2020/5/6.
 */
public class CharLeetcodeTest {


    @Test
    public void test() throws Exception {

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
     *
     * @param s
     * @return
     */
    public String longestPalindrome_methodA(String s) {

        int length = s.length();
        //边界判断
        if (length < 2) return s;
        //记录全局最大长度，因为s长度大于1所以至少为1
        int maxLength = 1;
        //记录左指针
        int left = 0;
        // s.charAt(i) 每次都会检查数组下标越界，因此先转换成字符数组
        char[] charArray = s.toCharArray();

        // 枚举所有长度大于 1 的子串 charArray[i..j],通过两层遍历
        for (int i = 0; i < length - 1; i++) {
            for (int j = i + 1; j < length; j++) {
                //剪枝，只判断大于当前最大值的string
                if (j - i + 1 > maxLength && validPalindromic(charArray, i, j)) {
                    maxLength = j - i + 1;
                    left = i;
                }
            }
        }
        return s.substring(left, left + maxLength);

    }


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
     * todo 动态规划，买看懂
     * 思路：
     * a.对于一个子串而言，如果它是回文串，并且长度大于 22，那么将它首尾的两个字母去除之后，它仍然是个回文串。例如对于字符串 \textrm{``ababa''}“ababa”，如果我们已经知道 \textrm{``bab''}“bab” 是回文串，那么 \textrm{``ababa''}“ababa” 一定是回文串
     * b.一般动态规划都会用二维数组进行记录
     *
     * @param s
     * @return
     */
    public String longestPalindrome_methodB(String s) {
        // 特判
        int len = s.length();
        if (len < 2) {
            return s;
        }

        int maxLen = 1;
        int begin = 0;

        // dp[i][j] 表示 s[i, j] 是否是回文串
        boolean[][] dp = new boolean[len][len];
        char[] charArray = s.toCharArray();

        //为啥这样，岂不是还有类似12这种没有遍历到
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
}