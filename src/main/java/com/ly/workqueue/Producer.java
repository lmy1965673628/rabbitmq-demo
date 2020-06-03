package com.ly.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.util.Scanner;

public class Producer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.180.161");
        f.setPort(5672);
        f.setUsername("guest");
        f.setPassword("guest");

        Connection c = f.newConnection();
        Channel ch = c.createChannel();
        //第二个参数设置队列持久化
        ch.queueDeclare("task_queue", true, false, false, null);

        while (true) {
            System.out.print("输入消息: ");
            String msg = new Scanner(System.in).nextLine();
            if ("exit".equals(msg)) {
                break;
            }
            //第三个参数设置消息持久化
            ch.basicPublish("", "task_queue", MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes("UTF-8"));
            System.out.println("消息已发送: " + msg);
        }

        c.close();
    }
}
