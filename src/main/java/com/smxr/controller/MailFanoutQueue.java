//package com.smxr.controller;
//
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
///**
// * @Author lzy
// * @Date 2021.03.24 17:44
// * @PC smxr
// * 监听mailQueue队列
// * 这种@RabbitListener和 @RabbitHandler一起使用的方式
// * 可以更具不同的参数形式使用不同的方法处理
// */
//@Component
//@RabbitListener(queues = {"mailQueue"})
//public class MailFanoutQueue {
//    @RabbitHandler
//    public void mailRabbitHandler(String message){
//        System.out.println("mailQueue fanout --- 收到消息"+message);
//    }
//    @RabbitHandler
//    public void mailRabbitHandler(byte[] message){
//        System.out.println("mailQueue fanout --- 收到消息"+message);
//    }
//}
