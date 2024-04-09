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

    /**
     * 单聊消息处理思路
     * <p>
     * 1.websocket服务将接收到消息，将消息发送至mq
     * 2.business业务服务监听mq的消息，对消息进行处理(持久化)
     * 3.business将消息发送到接受者对应的mq的queue中(queue为im服务的brokerId，通过redis构建的路由层要精准投递到消息接收者连接的websocket服务上)
     * 4.websocket服务接收到mq中的消息，将消息推送
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void messageReceiveHandler(ChannelHandlerContext ctx, Message msg) {

    }

}
