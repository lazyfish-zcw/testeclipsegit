package com.shengsiyuan.netty.thirdexample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE); // 保存channel

    /**
     * 服务端接收到任何客户端都会调用此程序
     * @param ctx
     * @param s
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
//        channelGroup.forEach(ch -> {
//            if (channel != ch) {
//                ch.writeAndFlush(ch.remoteAddress() + "发送的消息：" + msg);
//            } else {
//                ch.writeAndFlush("【自己】" + msg + "\n");
//            }
//        });

        for (Channel ch : channelGroup) {
            if (channel != ch) {
                ch.writeAndFlush(ch.remoteAddress() + "发送的消息：" + msg + "\n");
            } else {
                ch.writeAndFlush("【自己】" + msg + "\n");
            }
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel(); // 已经建立连接的的channel对象
        channelGroup.writeAndFlush("【服务器】<" + channel.remoteAddress() + ">加入\n");
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel(); // 已经建立连接的的channel对象
        channelGroup.writeAndFlush("【服务器】<" + channel.remoteAddress() + ">离开\n");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("【服务器】<" + channel.remoteAddress() + ">上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("【服务器】<" + channel.remoteAddress() + ">下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
