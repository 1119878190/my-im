package com.myim.decode;


import com.myim.proto.Message;
import com.myim.utils.ByteBufToMessageUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.util.List;

/**
 * 自定义解码器
 *
 * @author lx
 * @date 2023/05/01
 */
public class WebsocketMessageDecoder extends MessageToMessageDecoder<BinaryWebSocketFrame> {


    private static final int HEADER_LENGTH = 28;

    /**
     * 解码
     *
     * @param channelHandlerContext 通道处理程序上下文
     * @param binaryWebSocketFrame  字节缓冲区
     * @param list                  列表
     * @throws Exception 异常
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, BinaryWebSocketFrame binaryWebSocketFrame, List<Object> list) throws Exception {
        // 规定：
        //-------------------------------------------------------------------------------------------------------------------------------
        //|   4byte  |    4byte   |     4byte    |  4byte |      4byte    |       4byte   |    4byte       |   nbyte   |   nbyte
        //--------------------------------------------------------------------------------------------------------------------------------
        //| Command  |  version  |  clientType  |  appId  |   messageType |  imeiLength  |  contentLength |   imei      | contnet
        //------------------------------------------------------------------------------------------------------------------------------


        ByteBuf content = binaryWebSocketFrame.content();
        // 如果可读字节数小于28字节(前4x7)，说明不是一条完整的消息，直接返回
        if (content.readableBytes() < HEADER_LENGTH) {
            return;
        }
        Message message = ByteBufToMessageUtils.transition(content);
        if (message == null) {
            return;
        }
        list.add(message);

    }
}
