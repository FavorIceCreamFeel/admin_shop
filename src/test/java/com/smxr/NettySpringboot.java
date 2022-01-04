package com.smxr;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author lzy
 * @Date 2021.03.27 22:49
 * @PC smxr
 */
@SpringBootTest
public class NettySpringboot {
//    public static void main(String[] args) {
//        //.twr
//        try (FileChannel channel = new FileInputStream("data.txt").getChannel()) {
//            //准备缓存区
//            ByteBuffer allocate = ByteBuffer.allocate(10);
//            int read = channel.read(allocate);
//        } catch (IOException e) {
//        }
//    }
    //hello netty
public static void main(String[] args) {
        //再次分工创建一个独立的EventLoopGroup处理耗时操作
        EventLoopGroup group=new DefaultEventLoopGroup();//默认的EventLoopGroup只能处理普通任务和定时任务，无法处理io任务
        //1.启动器，负责组装Netty组件，启动服务
        new ServerBootstrap()
                //2. nio eventloop Group
                //分工调整：boss只负责NioSocketChannel上的accept事件/连接事件
                //		  worker只负责SocketChannel上的读写
                .group(new NioEventLoopGroup(),new NioEventLoopGroup())
                //3. 选择服务器的 serverSocketChannel 的实现
                .channel(NioServerSocketChannel.class)//oio bio nio
                //4. boss 负责处理连接 worker 负责处理读写，决定了worker能执行那些操作(handle)
                .childHandler(
                        //5. channel 代表和客户端进行数据读写的通道 Initializer初始化，负责添加别的handle
                        new ChannelInitializer<NioSocketChannel>() {
                    @Override       //连接后会调用
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        //6. 添加具体的 handle
                        //nioSocketChannel.pipeline().addLast(new StringDecoder()); //字符串处理器，将获取到的传输字节转换为字符串
                        nioSocketChannel.pipeline().addLast("handler1",new ChannelInboundHandlerAdapter(){
                            @Override       //读事件
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                //打印上一步转换好的字符串
                                System.out.println("handle1 netty ："+msg);
                                ctx.fireChannelRead(msg);//传递消息给下一个处理器handle2
                            }
                        }).addLast(group, "handle2", new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                                super.channelRead(ctx, msg);
                                System.out.println("handle2 netty ："+msg);
                            }
                        });//自定义处理器
                    }
                }).bind(8080);//7.绑定的监听端口
    }


}
