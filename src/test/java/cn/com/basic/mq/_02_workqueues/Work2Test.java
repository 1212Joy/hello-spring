package cn.com.basic.mq._02_workqueues;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhaijiayi on 2016/5/24.
 */
public class Work2Test {

    @Test
    public void testRecvExcute() throws Exception {
        Work work2 = new Work("workqueue");
        work2.recvExcute();
    }
}