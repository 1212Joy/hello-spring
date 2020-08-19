package cn.com.basic.leetcode;

import org.junit.Test;

import java.util.*;

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

    /**
     * todo 93-复原IP地址  (没明白)
     * 方法1-三层循环+剪枝
     * 方法2-思路+剪枝：回溯算法
     * 分析：
     *
     * @param s
     * @return
     */
    public List<String> restoreIpAddresses(String s) {
        return restoreIpAddresses_2(s);
    }


    public List<String> restoreIpAddresses_1(String s) {
        List<String> answer = new ArrayList<String>();
        if (s.length() > 12 || s.length() < 4)
            return answer;
        StringBuffer ip = new StringBuffer();
        for (int a = 1; a < 4; a++) {
            for (int b = 1; b < 4; b++) {
                for (int c = 1; c < 4; c++) {
                    int d = s.length() - a - b - c;
                    if (d > 0 && d < 4) {
                        int l1 = Integer.parseInt(s.substring(0, a));
                        int l2 = Integer.parseInt(s.substring(a, a + b));
                        int l3 = Integer.parseInt(s.substring(a + b, a + b + c));
                        int l4 = Integer.parseInt(s.substring(a + b + c));
                        if (l1 <= 255 && l2 <= 255 && l3 <= 255 && l4 <= 255) {
                            ip.append(l1);
                            ip.append(".");
                            ip.append(l2);
                            ip.append(".");
                            ip.append(l3);
                            ip.append(".");
                            ip.append(l4);
                            if (ip.length() == s.length() + 3)
                                answer.add(ip.toString());
                        }
                        //ip清空重新来
                        ip.delete(0, ip.length());
                    }
                }
            }
        }
        return answer;
    }


    private boolean f(String s) {
        if (s.length() == 0) return false;
        if (s.length() == 1) return true;
        if (s.length() > 3) return false;
        if (s.charAt(0) == '0') return false;
        if (Integer.parseInt(s) <= 255) return true;
        return false;
    }

    List<String> results = new ArrayList<>();

    public List<String> restoreIpAddresses_2(String s) {
        backtrack(s, new ArrayList<>(), 0);
        return results;
    }

    private void backtrack(String s, ArrayList<String> segment, int index) {
        if (segment.size() == 4 && index == s.length()) {
            results.add(String.join(".", segment));
            return;
        }
        for (int i = 1; i <= 3; i++) {
            if (index + i > s.length() || segment.size() > 4) break;
            String curr = s.substring(index, index + i);
            if ((i == 3 && Integer.parseInt(curr) > 255) || (curr.startsWith("0") && curr.length() > 1)) continue;
            segment.add(curr);
            backtrack(s, segment, index + i);
            segment.remove(segment.size() - 1);
        }
    }

    /**
     * 200. 岛屿数量
     * 题意：
     * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
     * 每座岛屿只能由水平和/或竖直方向上相邻的陆地连接而成
     * 解题:
     * 1.网格结构的 DFS 遍历
     * 扫描整个二维网格。如果一个位置为 1，则以其为起始节点开始进行深度优先搜索。在深度优先搜索的过程中，每个搜索到的 1 都会被重新标记为 0。
     * 2.广度优先
     *
     * @param grid
     * @return
     */
    public int numIslands(char[][] grid) {
        return numIslands_dfs(grid);
    }

    private int numIslands_dfs(char[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            //这个二维数组为啥j < grid[0].length
            for (int j = 0; j < grid[0].length; j++) {
                //如果遍历到1，就进行dfs
                if (grid[i][j] == '1') {
                    dfs(grid, i, j);
                    count++;
                }
            }
        }
        return count;
    }

    private void dfs(char[][] grid, int i, int j) {
        //到了网格边界或者碰到了0就结束
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] == '0') return;
        //将1重设为0
        grid[i][j] = '0';
        //对这个1的上下左右4个方向进行dfs操作
        dfs(grid, i + 1, j);
        dfs(grid, i, j + 1);
        dfs(grid, i - 1, j);
        dfs(grid, i, j - 1);
    }

    /**
     * todo 14. 最长公共前缀
     * <p>

     *
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        String prefix = strs[0];
        // 字符串可以被自身匹配，所以从第二个开始匹配。
        for (int i = 1; i < strs.length; i++) {
            // 如果匹配串不在要匹配字符串的开头，则表示不是要匹配字符串的前缀。
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) {
                    return "";
                }
            }
        }
        return prefix;
    }

    /**
     * todo 647. 列举全部回文子串

     * 方法2-动态规划
     *
     * @param s
     * @return
     */
    public int countSubstrings(String s) {
        if(s == null || s.equals("")){
            return 0;
        }
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int result = s.length();
        for(int i = 0; i<n; i++) dp[i][i] = true;
        for(int i = n-1; i>=0; i--){
            for(int j = i+1; j<n; j++){
                if(s.charAt(i) == s.charAt(j)) {
                    if(j-i == 1){
                        dp[i][j] = true;
                    }
                    else{
                        dp[i][j] = dp[i+1][j-1];
                    }
                }else{
                    dp[i][j] = false;
                }
                if(dp[i][j]){
                    result++;
                }
            }
        }
        return result;
    }
}