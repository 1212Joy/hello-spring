package cn.com.basic.leetcode;

import org.junit.Test;

import java.util.*;

/**
 * Created by zhaijiayi on 2020/5/6.
 */
public class CharLeetcodeTest {


    @Test
    public void test() throws Exception {
        System.out.println(decodeString("10[a]2[bc]"));
        ;
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
            // 如果匹配串不在要匹配字符串的开头，则表示不是要匹配字符串的前缀。indexOf这个啥意思

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
     * 72. 编辑距离
     * 题目：一定有解（添加、删除、替换）
     * 解法：
     * 方法1-暴力求解-BFS（放到Queue中）
     * 方法2-动态规划
     * 1）状态定义： DP[i][j]   i代表world1前i个字符替换到word2的前[j]个字符最少需要的操作步数。长度为DP[world1.length][world2.length]
     * 2）方程
     *
     * @param word1
     * @param word2
     * @return
     */
    public int minDistance(String word1, String word2) {
        int n1 = word1.length();
        int n2 = word2.length();
        int[][] dp = new int[n1 + 1][n2 + 1];
        // 填充值-第一行
        for (int j = 1; j <= n2; j++) dp[0][j] = dp[0][j - 1] + 1;
        // 填充值-第一列
        for (int i = 1; i <= n1; i++) dp[i][0] = dp[i - 1][0] + 1;
        //遍历
        for (int i = 1; i <= n1; i++) {
            for (int j = 1; j <= n2; j++) {
                //恰好不用做add、delete、replace操作
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j - 1], dp[i][j - 1]), dp[i - 1][j]) + 1;
                }

            }
        }
        return dp[n1][n2];
    }

    /**
     * 79. 单词搜索
     * 给定一个二维网格和一个单词，找出该单词是否存在于网格中。
     * <p>
     * 深度优先搜索+回溯详解
     * <p>
     * 更难的-212
     * 深度优先搜索+字典树
     *
     * @param board
     * @param word
     * @return
     */
    public boolean exist(char[][] board, String word) {
        //记录状态量
        boolean[][] visited = new boolean[board.length][board[0].length];
        //横轴遍历
        for (int i = 0; i < board.length; i++) {
            //纵轴遍历
            for (int j = 0; j < board[0].length; j++) {
                //第一个字符匹配，则开始dfs遍历。0代表第字符串第一个
                if (word.charAt(0) == board[i][j] && backtrack(i, j, 0, word, visited, board)) return true;
            }
        }
        return false;

    }

    /**
     * @param i
     * @param j
     * @param idx     - word长度
     * @param word
     * @param visited - 记录曾经走过的值是否匹配
     * @param board
     * @return
     */
    private boolean backtrack(int i, int j, int idx, String word, boolean[][] visited, char[][] board) {
        if (idx == word.length()) return true;
        //越界了，不够字符长度返回false。board[i][j] != word.charAt(idx)当前传进来的字符是否匹配
        if (i >= board.length || i < 0 || j >= board[0].length || j < 0 || board[i][j] != word.charAt(idx) || visited[i][j])
            return false;
        //记录回溯
        visited[i][j] = true;
        //上下左右都都遍历一遍，有一个为true就返回true，就沿着这个匹配的继续进行
        if (backtrack(i + 1, j, idx + 1, word, visited, board) || backtrack(i - 1, j, idx + 1, word, visited, board) || backtrack(i, j + 1, idx + 1, word, visited, board) || backtrack(i, j - 1, idx + 1, word, visited, board))
            return true;

        visited[i][j] = false; // 回溯
        return false;
    }


    /***
     *394. 字符串解码
     * 思路：栈
     * 本题难点在于括号内嵌套括号，需要从内向外生成与拼接字符串，这与栈的先入后出特性对应。
     * @param s
     * @return
     */
    public String decodeString(String s) {
        StringBuilder res = new StringBuilder();
        int multi = 0;
        //存数，
        Stack<Integer> stack_multi = new Stack<>();
        //存字符串
        Stack<String> stack_res = new Stack<>();
        for (Character c : s.toCharArray()) {
            if (c == '[') {
                stack_multi.add(multi);
                stack_res.add(res.toString());
                multi = 0;
                res = new StringBuilder();
                //遇到结束时，安装数组相乘
            } else if (c == ']') {
                StringBuilder tmp = new StringBuilder();
                int cur_multi = stack_multi.pop();
                for (int i = 0; i < cur_multi; i++) tmp.append(res);
                res = new StringBuilder(stack_res.pop() + tmp);
            } else if (c >= '0' && c <= '9') {
                //为什么*10->因为有可能嵌套。如果>10呢
                multi = multi * 10 + Integer.parseInt(c.toString());
            } else
                res.append(c);
        }
        return res.toString();
    }


    /**
     * 有效的字母异位词
     * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int length = t.length();
        Map<Character, Integer> char2count = new HashMap();
        for (int i = 0; i < length; i++) {
            char2count.put(s.charAt(i), char2count.getOrDefault(s.charAt(i), 0) + 1);
            char2count.put(t.charAt(i), char2count.getOrDefault(t.charAt(i), 0) - 1);
        }
        Iterator<Integer> val = char2count.values().iterator();
        while (val.hasNext()) {
            if (val.next() != 0) {
                return false;
            }
        }

        return true;
    }


}