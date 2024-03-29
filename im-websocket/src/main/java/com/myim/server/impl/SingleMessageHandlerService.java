package com.myim.server.impl;

import com.myim.proto.Message;
import com.myim.server.MessageHandlerService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

/**
 * 单聊消息处理
 *
 * @author lx
 * @date 2024/03/29
 */
@Component
public class SingleMessageHandlerService implements MessageHandlerService {

    @Override
    public void messageReceiveHandler(ChannelHandlerContext ctx, Message msg) {

    }

}
