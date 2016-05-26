package cn.com.basic.mq._01_helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;


/**
 * Created by zhaijiayi on 2016/5/17.
 */
public class RabbitMQRecv {
    private  String queueName = "";
    public RabbitMQRecv(String queueName){
        this.queueName = queueName;
    }
    public  void recvExcute() throws  Exception{
        // 创建链接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //默认链接的主机名
        factory.setHost("localhost");
        // 创建链接
        Connection connection = factory.newConnection();
        // 创建信息管道
        Channel channel = connection.createChannel();
        // 创建一个名为hello的队列，防止队列不存在
        channel.queueDeclare(queueName, false, false, false, null);//1.队列名2.是否持久化，3是否局限与链接，4不再使用是否删除，5其他的属性
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        // 声明一个消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);
        while (true) {
            // 循环获取信息
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();//指向下一个消息，如果没有会一直阻塞
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
        }
    }
}
