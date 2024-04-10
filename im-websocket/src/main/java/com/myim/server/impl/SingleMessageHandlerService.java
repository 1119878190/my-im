package com.myim.server.impl;

import com.alibaba.fastjson2.JSONObject;
import com.myim.model.SingleMessageModel;
import com.myim.proto.Message;
import com.myim.proto.MessageHeader;
import com.myim.server.MessageHandlerService;
import io.netty.channel.ChannelHandlerContext;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

import static com.myim.common.constant.TopicConstant.IM_2_BUSINESS_P2P_TOPIC;

/**
 * 单聊消息处理
 *
 * @author lx
 * @date 2024/03/29
 */
@Component
public class SingleMessageHandlerService implements MessageHandlerService {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

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
        MessageHeader messageHeader = msg.getMessageHeader();
        Object messagePack = msg.getMessagePack();

        SingleMessageModel singleMessageModel = JSONObject.parseObject(JSONObject.toJSONString(messagePack), SingleMessageModel.class);
        singleMessageModel.setSendTime(new Date());

        // todo 消息投递到业务服务mq的queue中
        rocketMQTemplate.asyncSend(IM_2_BUSINESS_P2P_TOPIC, JSONObject.toJSONString(singleMessageModel), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("消息发送成功");
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("消息发送失败");
            }
        });


        // todo 消息确认，给客户端发送ack，说明服务器收到了消息，保证消息可靠



    }

}
