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
// */
//@Component
//@RabbitListener(queues = {"phoneQueue"})
//public class PhoneFanoutQueue {
//    @RabbitHandler
//    public void PhoneRabbitHandler(String message){
//        System.out.println("phoneQueue fanout --- 收到消息"+message);
//    }
//
//}
