package cn.com.basic.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaijiayi on 2020/5/6.
 */
public class DemoTest {


    @Test
    public void test() throws Exception {
        List<List<Integer>> params = new ArrayList<>();
        List<Integer> params1 = new ArrayList<>();
        params1.add(2);
        params1.add(5);
        params1.add(10);
        params.add(params1);
        List<Integer> params2 = new ArrayList<>();
        params2.add(1);
        params2.add(8);
        params2.add(19);
        params.add(params2);
        MulitList mulitList = new MulitList(params);
        while (mulitList.hasNext()) {
            System.out.println("target - " + mulitList.next());
        }
    }


}

class MulitList {
    private List<Integer> sortedList;
    private int currentIndex = 0;
    private int maxSize = 0;


    public MulitList(List<List<Integer>> array) {
        //计算有多少了元素
        Map<Integer, Integer> outIndex2insideIndex = new HashMap<>();
        for (int i = 0; i < array.size(); i++) {
            maxSize = maxSize + array.get(i).size();
            outIndex2insideIndex.put(i, 0);
        }
        sortedList = new ArrayList<>(maxSize);
        //每个节点的筛选，都需要遍历全部k个节点
        Integer minVal = array.get(0).get(0);
        int[] minIndex = new int[2];
        while (true) {
            for (int i = 0; i < array.size(); i++) {
                int currentIndex = outIndex2insideIndex.get(i);
                if (currentIndex + 1 >= array.get(i).size()) {
                    continue;
                }
                if (array.get(i).get(currentIndex) < array.get(minIndex[0]).get(minIndex[1]) || minVal == null) {
                    minVal = array.get(i).get(currentIndex);
                    minIndex[0] = i;
                    minIndex[1] = currentIndex;

                }
            }
            //记录当前最小值
            sortedList.add(minVal);

            //重新更新最小值及最小指针
            minIndex[0] = minIndex[0];
            minIndex[1] = minIndex[1] + 1;
            if (currentIndex + 1 >= array.get(minIndex[0]).size()) {
                continue;
            }
            //map记录当前指针
            outIndex2insideIndex.put(minIndex[0], minIndex[1]);

            if (sortedList.size() == maxSize) {
                break;
            }
        }
    }

    public Integer next() throws Exception {
        currentIndex++;
        return sortedList.get(currentIndex - 1);

    }

    public boolean hasNext() throws Exception {
        return currentIndex <= maxSize;
    }
}
