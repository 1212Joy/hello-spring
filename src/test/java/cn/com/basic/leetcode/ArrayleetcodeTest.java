package cn.com.basic.leetcode;

import org.junit.Test;

import java.util.*;

/**
 * Created by zhaijiayi on 2020/5/6.
 */
public class ArrayleetcodeTest {


    @Test
    public void test() throws Exception {
        int[] array = {2, 3, -2, 4};
    }

    /**
     * 1. 两数之和
     * <p>
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     * <p>
     * 思路：用hash
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> value2index = new HashMap<>();
        //新数组声明
        int[] resArray = new int[2];
        for (int i = 0; i < nums.length; i++) {
            //判断当前这个值和map中是否存在相加=target
            if (value2index.containsKey(target - nums[i])) {
                resArray[0] = value2index.get(target - nums[i]);
                resArray[1] = i;
                return resArray;
            }
            //还没找到，就每次都value存在key中，value记录索引（返回的是索引）
            value2index.put(nums[i], i);
        }
        return resArray;
    }

    /**
     * 15. 三数之和
     * 排个序，然后首尾指针
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList();
        for (int i = 0; i < nums.length; i++) {
            int target = -nums[i];
            int l = i + 1;
            int r = nums.length - 1;
            if (nums[i] > 0)
                break;
            //nums[i] != nums[i - 1]  两个重复则返回的为重复值
            if (i == 0 || nums[i] != nums[i - 1]) {
                while (l < r) {
                    //三数相加为0
                    if (nums[l] + nums[r] == target) {
                        res.add(Arrays.asList(nums[i], nums[l], nums[r]));
                        //相等的指针要跳过
                        while (l < r && nums[l] == nums[l + 1]) l++;//如果两个值相等，左指针向后移，跳过这个
                        while (l < r && nums[r] == nums[r - 1]) r--;//如果两个值相等，右指针向前移，跳过这个
                        //左右指针移动
                        l++;
                        r--;
                        //小于0，移动左指针
                    } else if (nums[l] + nums[r] < target)
                        l++;
                        //大于0，移动右指针
                    else
                        r--;
                }
            }
        }
        return res;
    }


    /**
     * 33. 搜索旋转排序数组
     * <p>
     * 思路：二分法
     *
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        //返回值
        int res = -1;
        //边界场景
        if (nums == null || nums.length == 0) return target;
        if (nums.length == 1) return nums[0] == target ? 0 : res;
        //遍历数组收尾指针
        int left = 0;
        int right = nums.length - 1;
        //中间节点，当找到目标值则返回
        int mid;
        //!!注意等号
        while (left <= right) {
            //中间节点赋值
            mid = (left + right) / 2;
            if (nums[mid] == target) return mid;
            //!!先判断有序，在判断target所在区间
            //left到mid是有序的
            if (nums[left] <= nums[mid]) {
                //target在此区间，！！！注意=
                if (target >= nums[left] && target < nums[mid]) {
                    //注意 -1、+1
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                //target在此区间
                if (target <= nums[right] && target > nums[mid]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }

            }
        }
        return res;

    }


    /**
     * 56. 合并区间
     * 题意：合并重叠区间，没重叠的直接输出
     * todo 二维数组
     * 思路：先排序，再用栈保存，每次取最后一个元素进行比较
     *
     * @param intervals int[n][0]
     * @return
     */
    public int[][] merge(int[][] intervals) {
        int len = intervals.length;
        //边界判断
        if (len < 2) return intervals;
        // ！！！按照起点排序
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));
        //因为我们只关心结果集的最后一个区间 int[2] i长度只会=2   0-左边界、1-右边界
        Stack<int[]> res = new Stack<>();
        //放入第一个值
        res.add(intervals[0]);
        //从第二个值开始遍历，和前一个值比较
        for (int i = 1; i < len; i++) {
            int[] curInterval = intervals[i];
            // 每次新遍历到的列表与当前结果集中的最后一个区间的末尾端点进行比较
            int[] peek = res.peek();
            //如果当前值大于前一个，则没重合 不合并。
            if (curInterval[0] > peek[1]) {
                res.add(curInterval);
            } else {
                //!!! 需要合并，右边界要取最大的值
                peek[1] = Math.max(curInterval[1], peek[1]);
            }
        }
        //!!!方法转换
        return res.toArray(new int[res.size()][]);
    }


    /**
     * 215. 数组中的第K个最大元素 （和703一样）
     * <p>
     * 方法1 - 先排序在返回第k大哥元素   时间复杂度=O(logN)
     * 方法2 - 维护一个小顶堆，保存前k大个元素
     *
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {
        return findKthLargest_method2(nums, k);
    }

    private int findKthLargest_method1(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }

    private int findKthLargest_method2(int[] nums, int k) {
        PriorityQueue<Integer> res = new PriorityQueue(k);
        for (int num : nums) {
            //不到k个值
            if (res.size() < k) {
                res.offer(num);
                //大于第k个
            } else if (num > res.peek()) {
                res.poll();
                res.offer(num);
            }
        }
        return res.peek();
    }


    /**
     * 54. 螺旋矩阵
     * 给定一个包含 m x n 个元素的矩阵（m 行, n 列），请按照顺时针螺旋顺序，返回矩阵中的所有元素。
     * <p>
     * 解题：
     * 时间复杂度：O(mn)
     *
     * @param matrix
     * @return
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> list = new ArrayList<Integer>();
        if (matrix == null || matrix.length == 0)
            return list;
        int m = matrix.length;
        int n = matrix[0].length;
        int i = 0;

        //需要打印的层数 一层时count=0，统计矩阵从外向内的层数，如果矩阵非空，那么它的层数至少为1层
        int count = (Math.min(m, n) + 1) / 2;
        //从外部向内部遍历，逐层打印数据
        while (i < count) {
            //左->右
            for (int j = i; j < n - i; j++) {
                list.add(matrix[i][j]);
            }
            //上到下
            for (int j = i + 1; j < m - i; j++) {
                list.add(matrix[j][(n - 1) - i]);
            }
            //右到左
            for (int j = (n - 1) - (i + 1); j >= i && (m - 1 - i != i); j--) {
                list.add(matrix[(m - 1) - i][j]);
            }
            //下到上
            for (int j = (m - 1) - (i + 1); j >= i + 1 && (n - 1 - i) != i; j--) {
                list.add(matrix[j][i]);
            }
            i++;
        }
        return list;

    }


    /**
     * 88. 合并两个有序数组
     * <p>
     * m、n是啥意思？这道题啥意思？
     * <p>
     * 解题：
     * 方法、双指针、
     * * p1指向nums1有效尾部即m-1，p2指向nums2尾部即n-1，p3指向nums1尾部（nums1和nums2合并后尾部）即m+n-1
     * * 由于num1空间足够大，同时从有效尾部遍历num1和num2，比较大小，然后放在num1最后面
     * * 注意循环条件，nums2全部合并到nums1中，也就是p2>=0
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m - 1, p2 = n - 1, p3 = m + n - 1;
        while (p2 >= 0) {
            if (p1 >= 0 && nums1[p1] > nums2[p2]) {
                nums1[p3--] = nums1[p1--];
            } else {
                nums1[p3--] = nums2[p2--];
            }
        }
    }


    /**
     * 1470. 重新排列数组
     *
     * @param nums
     * @param n
     * @return
     */
    public int[] shuffle(int[] nums, int n) {
        int temp[] = new int[nums.length];
        int index = 0;
        for (int i = 0; i < n; i++) {
            temp[index++] = nums[i];
            temp[index++] = nums[i + n];
        }
        return temp;
    }


    /**
     * todo 31. 下一个排列  (看不懂)
     * 时间复杂度：O(n)，在最坏的情况下，只需要对整个数组进行两次扫描。
     * <p>
     * 空间复杂度：O(1)，没有使用额外的空间，原地替换足以做到。
     *
     * @param nums
     */
    public void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        while (i >= 0 && nums[i + 1] <= nums[i]) {
            i--;
        }
        if (i >= 0) {
            int j = nums.length - 1;
            while (j >= 0 && nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }
        reverse(nums, i + 1);
    }

    private void reverse(int[] nums, int start) {
        int i = start, j = nums.length - 1;
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 252. 会议室
     * <p>
     * 题目：
     * 请你判断一个人是否能够参加这里面的全部会议。
     * <p>
     * 思路：
     * 思路是按照开始时间对会议进行排序。接着依次遍历会议，检查它是否在下个会议开始前结束。
     * nlogn
     *
     * @param intervals
     * @return
     */
    public boolean canAttendMeetings(int[][] intervals) {
        //按照开始时间排序
        Arrays.sort(intervals, new Comparator<int[]>() {
            public int compare(int[] i1, int[] i2) {
                return i1[0] - i2[0];
            }
        });

        for (int i = 0; i < intervals.length - 1; i++) {
            //接着依次遍历会议，检查它是否在下个会议开始前结束
            if (intervals[i][1] > intervals[i + 1][0])
                return false;
        }
        return true;

    }

    /**
     * 253. 会议室 II
     * 0 - 开始
     * 1- 结束
     * 分别将开始和结束时间放到两个数组中，然后进行排序，虽然会打乱一个会议[开始，结束]
     * 但可行的原因是：当我们遇到“会议结束”事件时，意味着一些较早开始的会议已经结束。我们并不关心到底是哪个会议结束。我们所需要的只是 一些 会议结束,从而提供一个空房间。
     * <p>
     * 执行过程：两个指标从头比较，当开始<结束时，开始向后移动，否则开始和结束都向后移动
     * NlogN
     *
     * @param intervals
     * @return 请你计算至少需要多少间会议室，才能满足这些会议安排。
     */
    public int minMeetingRooms(int[][] intervals) {

        // Check for the base case. If there are no intervals, return 0
        if (intervals.length == 0) {
            return 0;
        }

        Integer[] start = new Integer[intervals.length];
        Integer[] end = new Integer[intervals.length];

        for (int i = 0; i < intervals.length; i++) {
            start[i] = intervals[i][0];
            end[i] = intervals[i][1];
        }

        // Sort the intervals by end time
        Arrays.sort(
                end,
                new Comparator<Integer>() {
                    public int compare(Integer a, Integer b) {
                        return a - b;
                    }
                });

        // Sort the intervals by start time
        Arrays.sort(
                start, new Comparator<Integer>() {
                    public int compare(Integer a, Integer b) {
                        return a - b;
                    }
                });

        // The two pointers in the algorithm: e_ptr and s_ptr.
        int startPointer = 0, endPointer = 0;

        // Variables to keep track of maximum number of rooms used.
        int usedRooms = 0;

        // Iterate over intervals.
        while (startPointer < intervals.length) {

            //意味着有结束的了，所以有会议室了，end可以移动
            if (start[startPointer] >= end[endPointer]) {
                endPointer += 1;
            } else {
                //否则没有可用的会议室+1
                usedRooms += 1;
            }
            startPointer += 1;
        }

        return usedRooms;
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
     * @return 岛屿数量
     */
    public int numIslands(int[][] grid) {
        return numIslands_dfs(grid);
    }

    private int numIslands_dfs(int[][] grid) {
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

    private void dfs(int[][] grid, int i, int j) {
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
     * todo 695. 岛屿的最大面积
     * <p>
     * 思路：DFS解法
     *
     * @param grid
     * @return 岛屿面积
     */
    public int maxAreaOfIsland(int[][] grid) {
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    res = Math.max(res, dfs(i, j, grid));
                }
            }
        }
        return res;
    }

    // 每次调用的时候默认num为1，进入后判断如果不是岛屿，则直接返回0，就可以避免预防错误的情况。
    // 每次找到岛屿，则直接把找到的岛屿改成0，这是传说中的沉岛思想，就是遇到岛屿就把他和周围的全部沉默。
    // ps：如果能用沉岛思想，那么自然可以用朋友圈思想。有兴趣的朋友可以去尝试。
    private int dfs(int i, int j, int[][] grid) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[i].length || grid[i][j] == 0) {
            return 0;
        }
        //沉岛
        grid[i][j] = 0;
        int num = 1;
        //上下左右
        num += dfs(i + 1, j, grid);
        num += dfs(i - 1, j, grid);
        num += dfs(i, j + 1, grid);
        num += dfs(i, j - 1, grid);
        return num;

    }
    /*---------------------------------------*/


}