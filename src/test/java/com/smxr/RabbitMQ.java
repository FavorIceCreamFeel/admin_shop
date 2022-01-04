//package com.smxr;
//
//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.ConnectionFactory;
////import com.smxr.controller.RabbitMQController;
//import com.smxr.myInterface.InJect;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.concurrent.TimeoutException;
//
///**
// * @Author lzy
// * @Date 2021.03.21 14:39
// * @PC smxr
// */
//@SpringBootTest
//public class RabbitMQ {
//    @Test
//    @InJect
//    void rabbitmq(){
//        ConnectionFactory connectionFactory = new ConnectionFactory();
//        connectionFactory.setHost("39.103.178.68");
//        connectionFactory.setPort(5672);
//        connectionFactory.setUsername("smxr");
//        connectionFactory.setPassword("liu150938");
//        connectionFactory.setVirtualHost("/");
//
//        try (Connection connection = connectionFactory.newConnection("生产者");){
//            Channel channel = connection.createChannel();//获取连接通道
//            String queueName="queuel";
//
//            channel.queueDeclare(queueName,false,false,false,null);
//            String message = "hello xuexiangban!!!";
//            channel.basicPublish("",queueName,null,message.getBytes());
//            System.out.println("发送成功！！！");
//
//            if (channel!=null&&channel.isOpen())
//                channel.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (TimeoutException e) {
//            e.printStackTrace();
//        }finally {
//        }
//    }
//    @Test
//    void rabbitmqis() throws InvocationTargetException, IllegalAccessException {
//        RabbitMQController rabbitMQController = new RabbitMQController();
//        Class<? extends RabbitMQController> aClass = rabbitMQController.getClass();
////        Method addOrder = aClass.getDeclaredMethod("addOrder", RabbitMQController.class);
//        Method addOrder1 = null;
//        Method addOrder=null;
//        try {
////            addOrder1 = aClass.getMethod("addOrder", RabbitMQController.class);
//             addOrder = aClass.getDeclaredMethod("addOrder");
//            addOrder.setAccessible(true);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        System.out.println("执行方法：=============");
//        addOrder.invoke(rabbitMQController);
//        System.out.println("执行完成：=============");
//    }
//}
