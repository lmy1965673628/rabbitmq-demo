package com.ly.topic;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Scanner;

public class Consumer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.102.205");
        f.setUsername("guest");
        f.setPassword("guest");
        Connection c = f.newConnection();
        Channel ch = c.createChannel();

        ch.exchangeDeclare("topic_logs", BuiltinExchangeType.TOPIC);

        //自动生成对列名,
        //非持久,独占,自动删除
        String queueName = ch.queueDeclare().getQueue();

        System.out.println("输入bindingKey,用空格隔开:");
        String[] a = new Scanner(System.in).nextLine().split("\\s");

        //把该队列,绑定到 topic_logs 交换机
        //允许使用多个 bindingKey
        for (String bindingKey : a) {
            ch.queueBind(queueName, "topic_logs", bindingKey);
        }

        System.out.println("等待接收数据");

        //收到消息后用来处理消息的回调对象
        DeliverCallback callback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                String msg = new String(message.getBody(), "UTF-8");
                String routingKey = message.getEnvelope().getRoutingKey();
                System.out.println("收到: "+routingKey+" - "+msg);
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
