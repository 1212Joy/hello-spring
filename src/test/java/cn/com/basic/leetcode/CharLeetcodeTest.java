package cn.com.basic.leetcode;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by zhaijiayi on 2020/5/6.
 */
public class CharLeetcodeTest {


    @Test
    public void test() throws Exception {

    }

    /**
     * 3. 无重复字符的最长子串
     * length = current-left
     * 用map记录value2index
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        int res = 0;
        if (s == null) {
            return res;
        }
        int leftIndex = 0;
        Map<Character, Integer> value2key = new HashMap<>();
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            //判断存在，移动完left指针，取当前index最大的
            if (value2key.containsKey(chars[i])) {
                //取重复的后一个index
                leftIndex = Math.max(value2key.get(chars[i]) + 1, leftIndex);
            }
            value2key.put(chars[i], i);
            res = Math.max(res, i - leftIndex + 1);
        }
        return res;
    }

    /**
     * 20. 有效的括号
     * <p>
     * 栈
     *
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        //将匹配的映射关系存起来,且key设为后符号
        Map<Character, Character> map = new HashMap();
        map.put(')', '(');
        map.put('}', '{');
        map.put(']', '[');
        Stack stack = new Stack();
        for (Character c : s.toCharArray()) {
            if (stack.isEmpty() || !map.containsKey(c)) {
                stack.push(c);
                continue;
            }
            if (stack.pop() != map.get(c)) {
                return false;
            }
        }
        return stack.isEmpty();
    }

    /**
     * 33. 搜索旋转排序数组
     * 思路：二分搜索
     * 时间复杂度：O(logn)
     *
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        int res = -1;
        //边界判断
        if (nums == null || nums.length == 0) return res;
        if (nums.length == 1) return nums[0] == target ? 0 : res;
        //设定左右两个指着
        int l = 0;
        int r = nums.length - 1;
        //判断条件包含=
        while (l <= r) {
            //中间节点索引计算
            int mid = l + ((l - r) / 2);
            //边界当中间节点命中
            if (nums[mid] == target) return mid;
            //0到中间是升序
            if (nums[l] <= nums[mid]) {
                //target在区间内，将右指针指向中间结点前一个
                if (nums[0] <= target && target < nums[mid]) {
                    r = mid - 1;
                    //target不在区间内，移动左指针
                } else {
                    l = mid + 1;

                }
                //旋转的节点在此区间内中间到结尾  假如这个区间最大值78456
            } else {
                if (nums[mid] < target && target < nums[r]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                    //target不在区间内，移动左指针
                }
            }

        }
        return res;
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