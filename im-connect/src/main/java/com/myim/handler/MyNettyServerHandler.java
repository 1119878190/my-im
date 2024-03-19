package com.myim.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class MyNettyServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelActive();
        System.out.println("channel 连接激活");
    }

    //表示 channel 处于不活动状态
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        //设置离线
        System.out.println("channel 不活动状态 关闭通道");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame s) throws Exception {
        String text = s.text();
        System.out.println("服务端收到消息：" + text);
        Channel channel = channelHandlerContext.channel();

        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame("服务端收到消息"+text);
        channel.writeAndFlush(textWebSocketFrame);
//        channel.writeAndFlush("服务端收到消息");
    }
}
