package com.shengsiyuan.netty.fourthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;

            String eventType = null;

            switch (event.state()) {
                case READER_IDLE:
                    eventType = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
                case ALL_IDLE:
                    eventType = "读写空闲";
                    break;
            }

            /**
             * 根据new IdleStateHandler(5, 7, 10, TimeUnit.SECONDS)的设置
             * server 5秒内没有读取到client的任何请求，触发读空闲事件
             * 7秒内没有响应client，触发写空闲事件
             * 10秒内没有读取到client的任何请求，也没有响应client，触发读写空闲事件
             */
            System.out.println(ctx.channel().remoteAddress() + "超时事件：" + eventType);

            ctx.channel().close();
        }

    }
}
