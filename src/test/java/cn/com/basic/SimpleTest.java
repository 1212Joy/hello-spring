package cn.com.basic;

import org.junit.Test;

/**
 * Created by zhaijiayi on 2016/3/10.
 */
public class SimpleTest {

    @Test
    public void randomDouble() throws Exception {
        
        java.util.Random random=new java.util.Random();
        int randomCode = random.nextInt(99)+1;
        double a = randomCode*0.01;
        System.out.println("随机生成： "+a);
    }


}