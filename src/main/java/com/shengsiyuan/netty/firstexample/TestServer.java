package com.shengsiyuan.netty.firstexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestServer {

    public static void main(String[] args) throws Exception {

        // 两个线程组(实现循环组)
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // 接受链接，发送给worker
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // worker处理

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class) // 通道
                    .childHandler(new TestServerInitializer()); // 子处理器
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync(); // 绑定端口
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
