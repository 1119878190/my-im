package com.myim.server.impl;

import com.alibaba.fastjson2.JSONObject;
import com.myim.proto.Message;
import com.myim.proto.MessagePack;
import com.myim.server.MessageHandlerService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 登录消息处理器
 *
 * @author lx
 * @date 2024/03/29
 */
@Component
public class LoginMessageHandlerService implements MessageHandlerService {


    @Override
    public void messageReceiveHandler(ChannelHandlerContext ctx, Message msg) {
        String message = JSONObject.toJSONString(msg);
        System.out.println("websocket：：：  接受到的数据：" + message);

        MessagePack<Object> messagePack = new MessagePack<>();
        messagePack.setCommand(10001);
        String resp = "[服务器在]" + LocalDateTime.now() + "接受到消息, 消息为：" + message;
        messagePack.setData(resp);
        ctx.writeAndFlush(messagePack);

        // todo 用户登录 构建 redis路由层，并把用户信息存入map换成，ctx也补充用户信息
    }
}
