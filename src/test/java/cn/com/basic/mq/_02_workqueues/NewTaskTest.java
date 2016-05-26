package cn.com.basic.mq._02_workqueues;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhaijiayi on 2016/5/24.
 */
public class NewTaskTest {

    @Test
    public void testSendExcute() throws Exception {
        String [] message ={"hhh","First message.","Second message..","Third message...","Fourth message....","Fifth message....."};
        NewTask newTask = new NewTask("workqueue",message);
        newTask.sendExcute();
    }
}