package com.smxr.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @Author lzy
 * @Date 2021.03.24 11:26
 * @PC smxr
 */
@Configuration
public class RabbitMQConfig {
    public static final String order_fanout_exchange="order_fanout_exchange";

    public static final String order_headers_exchange="order_headers_exchange";


    public static final String order_direct_exchange="order_direct_exchange";
    public static final String order_direct_exchange_mail="mail";
    public static final String order_direct_exchange_phone="phone";

    public static final String order_topic_exchange="order_topic_exchange";
    public static final String order_topic_exchange_mail="topic.#";
    public static final String order_topic_exchange_phone="topic.com.*";


    @Bean
    public FanoutExchange fanoutExchange(){
        //1.声明注册一个fanout模式的order_fanout_exchange交换机，参数：名字，是否持久化，是否自动删除
        return new FanoutExchange("order_fanout_exchange",true,false);
    }
    @Bean
    public DirectExchange directExchange(){
        //1.声明注册一个direct模式的order_topic_exchange交换机，参数：名字，是否持久化，是否自动删除
        return new DirectExchange(order_direct_exchange,true,false);
    }
    @Bean
    public TopicExchange topicExchange(){
        //1.声明注册一个topic模式的order_topic_exchange交换机，参数：名字，是否持久化，是否自动删除
        return new TopicExchange(order_topic_exchange,true,false);
    }
    @Bean
    public HeadersExchange headersExchange(){
        //1.声明注册一个headers模式的order_headers_exchange交换机，参数：名字，是否持久化，是否自动删除
        return new HeadersExchange(order_headers_exchange,true,false);
    }

    @Bean
    public DirectExchange deadDirectExchange(){
        //1.死信交换机
        return new DirectExchange("dead_direct_exchange",true,false);
    }
    @Bean
    public Queue deadQueue(){
        //2.死信队列
        return new Queue("deadQueue",true);
    }
    @Bean
    public Binding deadBinding(){
        //死信队列 --Binding--> dead_direct_exchange交换机
        return BindingBuilder.bind(deadQueue()).to(deadDirectExchange()).with("dead");
    }

    @Bean
    public Queue mailQueue(){
        //2.声明注册一个队列mailQueue，参数：名字，是否持久化
        return new Queue("mailQueue",true);
    }
    @Bean
    public Queue TTLQueue(){
        //给队列添加属性：5秒过期
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("x-message-ttl",5000);//int类型的毫秒数
        stringObjectHashMap.put("x-dead-letter-exchange","dead_direct_exchange");//交换机名字
        stringObjectHashMap.put("x-dead-letter-routing-key","dead");//因为是direct模式，所以是有routingkey的;   fanout模式就不用配置
        stringObjectHashMap.put("x-max-length",10);//设置队列长度 如果队列已满也会让消息进入死信队列
        //2.声明注册一个队列mailQueue，参数：名字，是否持久化
        return new Queue("TTLQueue",true,false,false,stringObjectHashMap);
    }
    @Bean
    public Queue phoneQueue(){
        //2.声明注册一个队列mailQueue，参数：名字，是否持久化
        return new Queue("phoneQueue",true);
    }
    @Bean
    public Binding mailBinding(){
        HashMap<String, Object> header = new HashMap<>();
        header.put("queue","mailQueue");
        header.put("bindType","whereAll");
        //3.完成绑定交互机    to.绑定交换机    .with()绑定路由 Routingkey
//        return BindingBuilder.bind(mailQueue()).to(directExchange()).whereAll(header).match();
        return BindingBuilder.bind(mailQueue()).to(directExchange()).with("mail");
    }
    @Bean
    public Binding phoneBinding(){
        HashMap<String, Object> header = new HashMap<>();
        header.put("queue","phoneQueue");
        header.put("bindType","whereAny");
        //3.完成绑定交互机
        return BindingBuilder.bind(phoneQueue()).to(directExchange()).with("phone");
    }
    @Bean
    public Binding TTLBinding(){
        //3.完成绑定交互机
        return BindingBuilder.bind(TTLQueue()).to(directExchange()).with("ttl");
    }
}
