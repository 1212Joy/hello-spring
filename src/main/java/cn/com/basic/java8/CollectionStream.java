package cn.com.basic.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by ZhaiJiaYi on 2016/8/12.
 *
 * Stream & Collection
 */
public class CollectionStream {
    public static List<String> lists;
    public static List<String> bigLists ;

    CollectionStream(List<String> temLists ){
        this.lists = temLists;
    }

    public void innitParallelDate(){
        this.bigLists = new ArrayList<>();
        for (int i = 0; i < 10000000; i++) {
            UUID uuid = UUID.randomUUID();
            bigLists.add(uuid.toString());
        }
    }
    /**中间操作**/
    // 1.过滤器（Filter）
     public  void streamFilterTest() {
        System.out.println("Method 1 : filter by start Character");
        lists.stream().filter((s -> s.startsWith("a"))).forEach(System.out::println);
        //等价于以上操作
        System.out.println("Method 2 : filter by start Character");
        Predicate<String> predicate = (s) -> s.startsWith("a");
        lists.stream().filter(predicate).forEach(System.out::println);

        //连续过滤
        System.out.println("Method 3 : filter by end Character");
        Predicate<String> predicate1 = (String s) -> s.endsWith("1");
        lists.stream().filter(predicate).filter(predicate1).forEach(System.out::println);
    }

    // 2.排序（Sorted）
    public  void streamSortedTest() {
        System.out.println("默认Comparator(升)");
        lists.stream().sorted().filter((s -> s.startsWith("a"))).forEach(System.out::println);

        System.out.println("自定义Comparator（降）");
        lists.stream().sorted((p1, p2) -> p2.compareTo(p1)).filter((s -> s.startsWith("a"))).forEach(System.out::println);

    }

    //3.映射（Map）
    public  void streamMapTest() {
        lists.stream().map(String::toUpperCase).sorted((a, b) -> b.compareTo(a)).forEach(System.out::println);

        System.out.println("自定义映射规则");
        Function<String, String> function = (p) -> {return p + ".txt";};
        lists.stream().map(String::toUpperCase).map(function).sorted((a, b) -> b.compareTo(a)).forEach(System.out::println);

    }
    /** 完结操作 **/

   // 1.匹配（Match） 用来判断某个predicate是否和流对象相匹配，最终返回Boolean类型结果，例如：
    public  void streamMatchTest() {
        //流对象中只要有一个元素匹配就返回true
        boolean anyStartWithA = lists.stream().anyMatch((s -> s.startsWith("a")));
        System.out.println(anyStartWithA);
        //流对象中每个元素都匹配就返回true
        boolean allStartWithA
                = lists.stream().allMatch((s -> s.startsWith("a")));
        System.out.println(allStartWithA);
    }

   // 2.收集（Collect） 将这些元素存至集合中，此时便可以使用Stream提供的collect方法收集，例如：
    public  void streamCollectTest() {
        List<String> list = lists.stream().filter((p) -> p.startsWith("a")).sorted().collect(Collectors.toList());
        System.out.println(list);

    }

   //3. 计数（Count）类似sql的count，用来统计流中元素的总数，例如：
    public  void streamCountTest() {
        long count = lists.stream().filter((s -> s.startsWith("a"))).count();
        System.out.println(count);
    }

  //4.规约（Reduce）reduce方法允许我们用自己的方式去计算元素或者将一个Stream中的元素以某种规律关联，例如：
    public  void streamReduceTest() {
        Optional<String> optional = lists.stream().sorted().reduce((s1, s2) -> {
            System.out.println(s1 + "|" + s2);
            return s1 + "|" + s2;
        });
    }


    /** 并行 串行 效率验证
     *
     * .stream() -> 串行
     * .parallelStream()  ->并行
     *
     * **/
    public void parallelStreamSorted() {
        innitParallelDate();
        notParallelStreamSortedTest();
        parallelStreamSortedTest();

    }
    private void notParallelStreamSortedTest() {
        long startTime = System.nanoTime();
        long count = bigLists.stream().sorted().count();
        long endTime = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println(System.out.printf("串行排序: %d ms", millis));

    }
    private void parallelStreamSortedTest() {
        long startTime = System.nanoTime();
        long count = bigLists.parallelStream().sorted().count();
        long endTime = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println(System.out.printf("并行排序: %d ms", millis));

    }
}

