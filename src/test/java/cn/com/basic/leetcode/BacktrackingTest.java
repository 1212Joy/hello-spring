package cn.com.basic.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhaijiayi on 2020/5/6.
 */
public class BacktrackingTest {


    @Test
    public void test() throws Exception {

    }
/*
回溯专题
39. 组合总和
40. 组合总和 II
46. 全排列
47. 全排列 II
78. 子集 （不含重复）
90. 子集 II （包含重复的）
*/

    /**
     * todo 46. 全排列  没明白
     * 题目：给定一个 没有重复 数字的序列，返回其所有可能的全排列。
     * 思路：回溯算法
     * 其实回溯算法关键在于:不合适就退回上一步
     * 然后通过约束条件, 减少时间复杂度.
     * <p>
     * 参考：https://leetcode-cn.com/problems/permutations/solution/quan-pai-lie-di-gui-hui-su-fa-jian-dan-yi-dong-by-/
     *
     * @param nums
     * @return
     */
    List<List<Integer>> resArray = new LinkedList<>();

    public List<List<Integer>> permute(int[] nums) {
        // 双端列表 记录「路径」
        LinkedList<Integer> track = new LinkedList<>();
        backtrack(nums, track);
        return resArray;
    }

    // 路径：记录在 track 中
// 选择列表：nums 中不存在于 track 的那些元素
// 结束条件：nums 中的元素全都在 track 中出现
    private void backtrack(int[] nums, LinkedList<Integer> track) {
        // 触发结束条件
        if (track.size() == nums.length) {
            resArray.add(new LinkedList(track));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            // 这个数被选过了
            if (track.contains(nums[i]))
                continue;
            // 当前位做选择
            track.add(nums[i]);
            // 继续对下一位选择
            backtrack(nums, track);
            // 递归结束，下一位无法选择递归返回了且没满足结束条件
            // 说明当前位的选择导致了下一位无论怎么选都不能到达结束条件
            // 所以当前位的选择是错误的，撤销当前位选择
            track.removeLast();
        }

    }

    /**
     * 78. 子集
     * <p>
     * 方法2： 回溯
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        backtrack(0, nums, res, new ArrayList<Integer>());
        return res;

    }

    /**
     * tmp为什么tmp这样
     *
     * @param i
     * @param nums
     * @param res
     * @param tmp
     */
    private void backtrack(int i, int[] nums, List<List<Integer>> res, ArrayList<Integer> tmp) {
        res.add(new ArrayList<>(tmp));
        for (int j = i; j < nums.length; j++) {
            tmp.add(nums[j]);
            backtrack(j + 1, nums, res, tmp);
            tmp.remove(tmp.size() - 1);
        }
    }

}