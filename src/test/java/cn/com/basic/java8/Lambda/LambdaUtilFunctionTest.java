package cn.com.basic.java8.Lambda;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ZhaiJiaYi on 2016/8/12.
 */
public class LambdaUtilFunctionTest {

    LambdaUtilFunction lambdaUtilFunction = new LambdaUtilFunction();
    @Test
    public void testPredicateTest() throws Exception {
        lambdaUtilFunction.predicate();
    }

    @Test
    public void testFunctionTest() throws Exception {
        lambdaUtilFunction.function();
    }

    @Test
    public void testSupplierTest() throws Exception {
        lambdaUtilFunction.supplier();
    }

    @Test
    public void testConsumerTest() throws Exception {
        lambdaUtilFunction.consumer();
    }
}