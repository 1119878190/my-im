package com.myim.initializer;

import com.myim.handler.MyNettyServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class MyNettyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 对写大数据流的支持
        // websocket 基于http协议，所以要有http编解码器
        pipeline.addLast("http-codec", new HttpServerCodec());
        // 对写大数据流的支持
//        pipeline.addLast("http-chunked", new ChunkedWriteHandler());
//        // 几乎在netty中的编程，都会使用到此handler
//        pipeline.addLast("aggregator", new HttpObjectAggregator(65535));
        /**
         * websocket 服务器处理的协议，用于指定给客户端连接访问的路由 : /ws
         * 本handler会帮你处理一些繁重的复杂的事
         * 会帮你处理握手动作： handshaking（close, ping, pong） ping + pong = 心跳
         * 对于websocket来讲，都是以frames进行传输的，不同的数据类型对应的frames也不同
         */
//        pipeline.addLast(new StringDecoder());
//        pipeline.addLast(new StringEncoder());
        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
        // 客户端连接地址为 ws://localhost:8000/ws
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        // 目前暂定 字符串编解码，后续改为自定义编解码器解决粘包拆包问题

        pipeline.addLast(new MyNettyServerHandler());
    }
}
