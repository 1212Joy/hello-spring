package cn.com.basic.mq._03_bindings_exchanges;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhaijiayi on 2016/5/24.
 */
public class ReceiveLogsToSaveTest {

    @Test
    public void testExcute() throws Exception {
        ReceiveLogsToSave receiveLogsToSave = new ReceiveLogsToSave();
        receiveLogsToSave.excute();
    }
}