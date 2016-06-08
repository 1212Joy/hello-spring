package cn.com.basic.mq._03_fanout_exchanges;

import org.junit.Test;

/**
 * Created by zhaijiayi on 2016/5/24.
 */
public class ReceiveLogsToConsoleTest {

    @Test
    public void testExcute() throws Exception {
        ReceiveLogsToConsole receiveLogsToConsole = new ReceiveLogsToConsole();
        receiveLogsToConsole.excute();
    }
}