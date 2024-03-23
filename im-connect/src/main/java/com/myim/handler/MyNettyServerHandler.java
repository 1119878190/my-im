package com.myim.handler;

import com.alibaba.fastjson2.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MyNettyServerHandler extends SimpleChannelInboundHandler<Object> {


    private WebSocketServerHandshaker handshaker;

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


    /**
     * 处理发送的数据
     * 对http，websocket单独处理
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {// 如果是HTTP请求，进行HTTP操作
            handleHttpRequest(ctx, (DefaultHttpRequest)msg);
        } else if (msg instanceof WebSocketFrame) {// 如果是Websocket请求，则进行websocket操作
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }


    /**
     * 对http请求的处理
     */
    private void handleHttpRequest(ChannelHandlerContext ctx,
                                   DefaultHttpRequest req) throws Exception {
        log.info("这里再处理http请求");
        // 如果 http解码失败 返回错误
        if (!req.getDecoderResult().isSuccess()) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.BAD_REQUEST));
            return;
        }

        // 如果是 websocket 握手
        if (("websocket".equals(req.headers().get("Upgrade")))) {
            WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                    "ws://localhost:8080/ws", null, false);
            handshaker = wsFactory.newHandshaker(req);
            if (handshaker == null) {
                WebSocketServerHandshakerFactory
                        .sendUnsupportedWebSocketVersionResponse(ctx.channel());
            } else {
                handshaker.handshake(ctx.channel(), req);
            }
            return;
        }
        // http请求
        String uri = req.getUri();
        Map<String,String> resMap = new HashMap<>();
        resMap.put("method",req.getMethod().name());
        resMap.put("uri",uri);
        String msg = "<html><head><title>test</title></head><body>你的请求为：" + JSON.toJSONString(resMap) +"</body></html>";
        // 创建http响应
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
        // 设置头信息
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");
        // 将html write到客户端
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);


    }

    /**
     * 对socket请求的处理
     */
    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame msg) {
        // 获取客户端传输过来的消息
        String content = ((TextWebSocketFrame) msg).text();
        System.out.println("websocket：：：  接受到的数据：" + content);
        ctx.writeAndFlush(new TextWebSocketFrame("[服务器在]" + LocalDateTime.now() + "接受到消息, 消息为：" + content));

    }


    private static void sendHttpResponse(ChannelHandlerContext ctx,
                                         DefaultHttpRequest req, FullHttpResponse res) {
        // 返回应答给客户端
        if (res.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(),
                    CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            HttpHeaders.setContentLength(res, res.content().readableBytes());
        }

        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpHeaders.isKeepAlive(req) || res.getStatus().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

}
