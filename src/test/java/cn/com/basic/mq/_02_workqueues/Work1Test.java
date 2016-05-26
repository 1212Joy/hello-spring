package cn.com.basic.mq._02_workqueues;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhaijiayi on 2016/5/24.
 */
public class Work1Test {

    @Test
    public void testRecvExcute() throws Exception {
        Work work1 = new Work("workqueue");
        work1.recvExcute();
    }
}