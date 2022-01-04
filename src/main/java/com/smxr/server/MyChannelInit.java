package com.smxr.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Author lzy
 * @Date 2021.04.01 17:52
 * @PC smxr
 * 客户端连接后调用此类
 */
public class MyChannelInit extends ChannelInitializer<NioSocketChannel> {
    //channel 代表和客户端进行数据读写的通道 Initializer初始化，负责添加别的handle
    @Override
    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
        System.out.println("进入initChannel...");
        nioSocketChannel.pipeline().addLast(new StringDecoder());//解码过滤
        nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
            @Override //读事件
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                System.out.println("读事件:"+msg); //3
                super.channelRead(ctx, msg);
            }

            @Override //读事件完成
            public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                System.out.println("读事件完成:");//4
                super.channelReadComplete(ctx);
            }

            @Override //连接注册事件
            public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                System.out.println("连接注册事件:");// 1
                super.channelRegistered(ctx);
            }

            @Override //连接注销事件
            public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
                System.out.println("连接注销事件:"); //6
                super.channelUnregistered(ctx);
            }

            @Override //连接建立事件
            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                System.out.println("连接建立事件:"); //2  建立连接获取到channel
                super.channelActive(ctx);
            }

            @Override //连接关闭事件
            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                System.out.println("连接关闭事件:"); //5 channel连接close之后
                super.channelInactive(ctx);
            }

            @Override //用户自定义事件
            public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                System.out.println("用户自定义事件:");
                super.userEventTriggered(ctx, evt);
            }

            @Override //Channel 可写状态变化事件
            public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
                System.out.println("可写状态变化事件:");
                super.channelWritabilityChanged(ctx);
            }

            @Override //异常通知事件
            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                System.out.println("异常通知事件:");
                super.exceptionCaught(ctx, cause);
            }
        });

    }
}
