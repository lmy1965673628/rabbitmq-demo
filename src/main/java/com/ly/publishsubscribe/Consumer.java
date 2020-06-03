package com.ly.publishsubscribe;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.180.161");
        f.setUsername("guest");
        f.setPassword("guest");
        Connection c = f.newConnection();
        Channel ch = c.createChannel();

        //定义名字为 logs 的交换机, 它的类型是 fanout
        ch.exchangeDeclare("logs", "fanout");

        //自动生成对列名,
        //非持久,独占,自动删除
        String queueName = ch.queueDeclare().getQueue();

        //把该队列,绑定到 logs 交换机
        //对于 fanout 类型的交换机, routingKey会被忽略
        ch.queueBind(queueName, "logs", "");

        System.out.println("等待接收数据");

        //收到消息后用来处理消息的回调对象
        DeliverCallback callback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                String msg = new String(message.getBody(), "UTF-8");
                System.out.println("收到: "+msg);
            }
        };

        //消费者取消时的回调对象
        CancelCallback cancel = new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {
            }
        };

        ch.basicConsume(queueName, true, callback, cancel);
    }
}
