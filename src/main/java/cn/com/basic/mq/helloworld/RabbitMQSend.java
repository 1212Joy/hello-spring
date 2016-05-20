package cn.com.basic.mq.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by zhaijiayi on 2016/5/17.
 */
public class RabbitMQSend {
    private  String queueName = "";
    private  String message = "";
    public RabbitMQSend(String queueName,String message){
        this.queueName = queueName;
        this.message = message;
    }
    public  void sendExcute() throws  Exception{
        // 创建链接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        // 创建链接
        Connection connection = factory.newConnection();
        // 创建信息管道
        Channel channel = connection.createChannel();
        // 创建一个名为hello的队列
        channel.queueDeclare(queueName, false, false, false, null);
        // 在RabbitMQ中，消息是不能直接发送到队列，它需要发送到交换器（exchange）中。
        // 第一参数空表示使用默认exchange，第二参数表示发送到的queue，第三参数是发送的消息是（字节数组）
        channel.basicPublish("", queueName, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();//关闭管道
        connection.close();//关闭连接
    }


}
