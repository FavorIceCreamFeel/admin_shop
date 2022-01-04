package com.smxr.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author lzy
 * @Date 2021.04.01 17:48
 * @PC smxr
 */

public class NettyServer {

    public static void run(){
        final NioEventLoopGroup parentGroup = new NioEventLoopGroup();
        final NioEventLoopGroup childGroup = new NioEventLoopGroup();
        final ChannelFuture bind = new ServerBootstrap()
                .group(parentGroup, childGroup)//nio eventloop Group
                .channel(NioServerSocketChannel.class)//选择服务器的 serverSocketChannel 的实现
                //option()设置的是服务端用于接收进来的连接，也就是boosGroup线程。
                //childOption()是提供给父管道接收到的连接，也就是workerGroup线程。
                //.option(ChannelOption.SO_BACKLOG,128)// 设置线程队列得到连接个数
                //.childOption(ChannelOption.SO_KEEPALIVE,true) //设置保持活动连接状态
                .childHandler(new MyChannelInit())// workerGroup  添加处理Handler   NioSocketChannel泛型的原因是客户端使用的是这个类
                //boosGroup 添加处理连接处理
                .bind(8080);//监听端口
    }

    public static void main(String[] args) {
        run();
    }
}

