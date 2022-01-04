//package com.smxr.controller;
//
//import com.rabbitmq.client.Channel;
//import com.smxr.config.RabbitMQConfig;
//import com.sun.el.stream.Stream;
//import org.springframework.amqp.AmqpException;
//import org.springframework.amqp.core.Correlation;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.MessagePostProcessor;
//import org.springframework.amqp.core.MessageProperties;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.rabbit.connection.CorrelationData;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.AmqpHeaders;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Headers;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//import sun.rmi.runtime.Log;
//
//import java.io.IOException;
//import java.lang.reflect.Method;
//import java.util.Arrays;
//import java.util.IntSummaryStatistics;
//import java.util.Map;
//import java.util.UUID;
//
///**
// * @Author lzy
// * @Date 2021.03.24 11:14
// * @PC smxr
// */
//@RestController
//@RequestMapping("/rabbitMQ")
//public class RabbitMQController {
//    @Autowired
//    private RabbitTemplate rabbitMQ; //注入core核心模板类
//    @RequestMapping("/addOrder")
//    public void addOrder() throws InterruptedException {
//        //1：根据商品查询库存是否充足
//        //2：创建订单
//        String orderID= UUID.randomUUID().toString();
//        //3：通过MQ来完成消息的分发
//        String exchangeName="order_fanout_exchange";
//        String routingKey="";
//        //参数：交换机，路由key/queue队列名称，消息内容
////        rabbitMQ.convertAndSend(exchangeName,routingKey,orderID);//fanout发送消息
//        //direct 发送消息
////        rabbitMQ.convertAndSend(RabbitMQConfig.order_direct_exchange,RabbitMQConfig.order_direct_exchange_mail,orderID);
////        rabbitMQ.convertAndSend(RabbitMQConfig.order_direct_exchange,RabbitMQConfig.order_direct_exchange_phone,orderID);
//        //topic发送消息
////        rabbitMQ.convertAndSend(RabbitMQConfig.order_topic_exchange,"topic","topic :"+orderID);
////        rabbitMQ.convertAndSend(RabbitMQConfig.order_topic_exchange,"topic.com.phone","topic.com.phone :"+orderID);
//
///*
//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setHeader("queue","mailQueue");
//        messageProperties.setHeader("bindType","whereAll");
//        Message message = new Message(orderID.getBytes(), messageProperties);
//        rabbitMQ.convertAndSend(RabbitMQConfig.order_headers_exchange,null,message);
//
//        Thread.sleep(3000);
//
//        MessageProperties messageProperties1 = new MessageProperties();
//        messageProperties1.setHeader("queue","phoneQueue");
//        messageProperties1.setHeader("bindType","whereAny");
//        rabbitMQ.convertAndSend(RabbitMQConfig.order_headers_exchange,null,new Message(orderID.getBytes(),messageProperties1));
//*/
////        rabbitMQ.convertAndSend(RabbitMQConfig.order_direct_exchange,"ttl",rabbitMQ.getUUID());
//        //设置一些附属属性
//        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
//            @Override
//            public Message postProcessMessage(Message message) throws AmqpException {
//                message.getMessageProperties().setExpiration("10000");
//                message.getMessageProperties().setContentEncoding("UTF-8");
//                return message;
//            }
//        };
//        //参数：交换机，路由key/queue队列名称，消息内容, 消息发送方式
//        rabbitMQ.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
//            @Override//CorrelationData 可以获取到消息id 和 消息内容 ，ack是否应答成功 ，s 失败的原因
//            public void confirm(CorrelationData correlationData, boolean ack, String s) {
//                System.out.println(" setConfirmCallback ack : "+ ack);
//                System.out.println(" setConfirmCallback correlationData : "+ correlationData.getId());
//                System.out.println(" setConfirmCallback correlationData Body : "+ correlationData.getReturnedMessage().getBody().toString());
//                System.out.println(" setConfirmCallback String : "+ s);
//            }
//        });
////        rabbitMQ.setReturnCallback(new RabbitTemplate.ReturnCallback() {
////            @Override
////            public void returnedMessage(Message message, int i, String s, String s1, String s2) {
////                System.out.println();("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", i, replyCode, replyText, exchange, routingKey);
////
////            }
////        });
//        rabbitMQ.convertAndSend(RabbitMQConfig.order_direct_exchange,"mail",rabbitMQ.getUUID(),messagePostProcessor);
//
//    }
//    @RabbitListener(queues = "mailQueue")
//    public void mailQueue(String message, Channel channel,
//                          @Header(AmqpHeaders.DELIVERY_TAG)long tag) throws IOException {
//        System.out.println("mailQueue  --- 收到消息"+message);
//        //手动签收
//        channel.basicAck(tag,false);//手动应答
//
//        //参数1 消息的tag ，参数2：多条处理，参数3：是否重发
//        //channel.basicNack(tag,false); //拒绝签收
//        //channel.basicNack(tag,false,false); //不在重发，让消息去死信队列
//    }
//    @RabbitListener(queues = "phoneQueue")
//    public void phoneQueue(String message,Channel channel){
//       System.out.println("phoneQueue  --- 收到消息"+message);
//    }
//
//
//}
