package com.smxr;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.resolver.InetSocketAddressResolver;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @Author lzy
 * @Date 2021.03.28 00:00
 * @PC smxr
 */
@SpringBootTest
public class NettyClient {
    public static void main(String[] args) {

        final NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        final ChannelFuture localhost = new Bootstrap()
                .group(eventExecutors)//只有一个NioEventLoopGroup
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        System.out.println("开始调用：initChannel()...");
                        nioSocketChannel.pipeline().addLast(new StringEncoder());
                        nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                super.channelRead(ctx, msg);
                            }
                        });
                    }
                }).connect(new InetSocketAddress("localhost", 8080));



        //监听是否连接成功,成功后调用operationComplete(完整的开始运转)
        localhost.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                System.out.println("连接成功进入回调函数...");
                System.out.println("开始调用：operationComplete...");
                System.out.println("打印 localhost："+localhost.toString());
                System.out.println("打印 channelFuture："+channelFuture.toString());
                final Channel channel = channelFuture.channel();
                channel.writeAndFlush("nihao,123,你好");
                System.out.println("打印 channel："+channel.toString());
                channel.close();
                System.out.println("channel.close()...");
            }
        });
        //按照顺序执行ChannelFutureListener 第一个是开始调用，后面的是关闭调用
        localhost.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                System.out.println("成功进入channelFuture关闭回调函数...");
                eventExecutors.shutdownGracefully();
                System.out.println("关闭工作组");
                System.out.println("END");
            }
        });


    }







/*    public static void main(String[] args) throws InterruptedException {
        //1.创建启动类
        new Bootstrap()
                //2. 添加event（事件）loop（循环）
                .group(new NioEventLoopGroup())
                //3.选择客户端channel实现
                .channel(NioSocketChannel.class)
                //4.添加处理器
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override       //连接后被调用
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new StringEncoder());//把字符串编码成字节
                    }
                })
                //连接Netty服务器
                .connect(new InetSocketAddress("localhost",8080))
                .sync() //阻塞方法，直到连接建立
                //获取通道
                .channel()//代表连接对象
                //6.向服务器发送数据
                .writeAndFlush("hello!, Netty");//发送数据

    }*/

/*    public static void main(String[] args) {
       //1.创建事件循环组
        EventLoopGroup group=new NioEventLoopGroup();//io事件，普通事件，定时事件
        EventLoopGroup defaultGroup=new DefaultEventLoopGroup();//普通任务，定时任务
        //2.获取下一个事件循环对象   //循环获取
        group.next();//获取到第一个事件循环对象
        group.next();//获取到第二个事件循环对象
        //3.执行普通任务  submit也可已使用
        group.next().execute(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("执行普通任务：OK");//后执行
        });
        System.out.println("main end");//先执行
        //下面是打印--------------
        //main end
        //执行普通任务：OK
        //总结：多线程执行普遍任务

        //定时任务  参数：1.执行类，2.初始时执行延迟时间 0/立即执行
        // 3.间隔执行时间 多久执行一次 4.时间单位
        group.next().scheduleAtFixedRate(() -> {
            System.out.println("定时任务执行：");
        },0,10, TimeUnit.SECONDS);

        System.out.println("scheduleAtFixedRate main end");
    }*/

//    public static void main(String[] args) throws InterruptedException {
//        final NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
//        //1.创建启动类
//        final ChannelFuture localhost = new Bootstrap()
//                //2. 添加event（事件）loop（循环）
//                .group(eventExecutors)
//                //3.选择客户端channel实现
//                .channel(NioSocketChannel.class)
//                //4.添加处理器
//                .handler(new ChannelInitializer<NioSocketChannel>() {
//                    @Override       //连接后被调用
//                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
//                        nioSocketChannel.pipeline().addLast(new StringEncoder());//把字符串编码成字节
//                    }
//                })
//                //连接Netty服务器
//                //异步调用 ，非阻塞的就是再起一个线程去连接，这个线程是NioEventLoopGroup中的线程
//                .connect(new InetSocketAddress("localhost", 8080));
//        //如果没有这个命令，下面获取的channel是空的
//        //必须要等到connect所启动的连接线程成功连接后才能获取通道channel
//        localhost.sync(); //阻塞方法，直到连接建立
//                //获取通道
//        final Channel channel = localhost.channel();
//       // 代表连接对象
//               // 6.向服务器发送数据
//        channel.writeAndFlush("hello!, Netty");//发送数据
//
//        channel.close();
//
//        //阻塞等待关闭同步
//        channel.closeFuture().sync();
//        //关闭eventloop组
//        eventExecutors.shutdownGracefully();
//
//        //添加一个监听器，EventLoop连接成功后会调用这个operationComplete方法
////        localhost.addListener(new ChannelFutureListener() {
////            @Override
////            public void operationComplete(ChannelFuture channelFuture) throws Exception {
////                channelFuture.channel().writeAndFlush("hello!, Netty");
////            }
////        });
//
//
//    }
}
