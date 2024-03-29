package com.myim.server;

import com.myim.proto.Message;
import io.netty.channel.ChannelHandlerContext;

/**
 * 消息处理
 *
 * @author lx
 * @date 2024/03/29
 */
public interface MessageHandlerService {


    /**
     * 消息处理
     *
     * @param ctx channel上下文
     * @param msg 消息
     */
    void messageReceiveHandler(ChannelHandlerContext ctx, Message msg);

}
