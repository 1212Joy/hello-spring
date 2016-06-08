package cn.com.basic.mq._04_direct_exchanges;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhaijiayi on 2016/6/3.
 */
public class ReceiveLogsDirectTest2 {

    @Test
    public void testExcute() throws Exception {
        new ReceiveLogsDirect("error").excute();

    }
}