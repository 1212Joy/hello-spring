package cn.com.basic;

import cn.com.basic.mq.helloworld.RabbitMQRecv;
import cn.com.basic.mq.helloworld.RabbitMQSend;
import cn.com.basic.mq.workqueues.NewTask;
import cn.com.basic.mq.workqueues.Work;
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

    @Test
    public void parseJsonString() throws Exception {
        String job_key  = String.format("job_%s","aaa");
        System.out.println("数值为: "+job_key);
    }

    @Test
    public void send() throws Exception {
        RabbitMQSend rabbitMQSend1 = new RabbitMQSend("hello1","hello baby1");
        rabbitMQSend1.sendExcute();

    }
    @Test
    public void taskTest() throws Exception {
        String [] message ={"First message.","Second message..","Third message...","Fourth message....","Fifth message....."};
        NewTask newTask = new NewTask("task_queue",message);
        newTask.sendExcute();

    }
    @Test
    public void work1() throws Exception {
        Work work1 = new Work("task");
        work1.recvExcute();
    }
    @Test
    public void work2() throws Exception {
        Work work2 = new Work("task");
        work2.recvExcute();
    }
    @Test
    public void stringArray() throws Exception {
        String a = "First message.Second message Third message Fourth message Fifth  message";
        for (char ch: a.toCharArray()) {
            if (ch == '.') System.out.println(ch);
        }

    }
}