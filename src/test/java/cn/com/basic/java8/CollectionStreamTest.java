package cn.com.basic.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhaiJiaYi on 2016/8/15.
 */
public class CollectionStreamTest {

    public List <String> init() {
        List<String> temLists = new ArrayList<String >();
        temLists.add("a1");
        temLists.add("a8");
        temLists.add("a2");
        temLists.add("b1");
        temLists.add("b2");
        temLists.add("b3");
        temLists.add("o1");

        return temLists;
    }
    CollectionStream lambdaStream = new CollectionStream(init());


    @Test
    public void testStreamFilterTest() throws Exception {

    }

    @Test
    public void testStreamSortedTest() throws Exception {
        lambdaStream.streamSortedTest();
    }

    @Test
    public void testStreamMapTest() throws Exception {
        lambdaStream.streamMapTest();
    }

    @Test
    public void testStreamMatchTest() throws Exception {
        lambdaStream.streamMatchTest();
    }

    @Test
    public void testStreamCollectTest() throws Exception {
        lambdaStream.streamCollectTest();
    }

    @Test
    public void testStreamCountTest() throws Exception {
        lambdaStream.streamCountTest();
    }

    @Test
    public void testStreamReduceTest() throws Exception {
        lambdaStream.streamReduceTest();

    }

    @Test
    public void testParallelStreamSorted() throws Exception {
        lambdaStream.parallelStreamSorted();
    }

}