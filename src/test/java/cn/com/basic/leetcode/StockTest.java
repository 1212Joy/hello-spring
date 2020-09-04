package cn.com.basic.leetcode;

import org.junit.Test;

/**
 * Created by zhaijiayi on 2020/5/6.
 */
public class StockTest {


    @Test
    public void test() throws Exception {

    }
    /**
     * 股票买卖系列 - 121、122、123、309、188、714（最难用三维数组表示状态）
     * <p>
     * 121. 买卖股票的最佳时机
     * 题目：整个过程最多一笔交易
     * todo 原因解释：
     * 假如计划在第 i 天卖出股票，那么最大利润的差值一定是在[0, i-1] 之间选最低点买入；
     * 所以遍历数组，依次求每个卖出时机的的最大差值，再从中取最大值。
     * 时间复杂度：O(n)O(n)，只需要遍历一次。
     * 空间复杂度：O(1)O(1)，只使用了常数个变量。
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        //初始化设置一个最大值
        int subPrice = Integer.MAX_VALUE;
        int resProfit = 0;
        for (int price : prices) {
            if (price < subPrice)
                subPrice = price;
            else if (price - subPrice > resProfit)
                resProfit = price - subPrice;
        }
        return resProfit;
    }

}