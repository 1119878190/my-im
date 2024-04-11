package com.myim.mq;

import com.alibaba.fastjson2.JSONObject;
import com.myim.context.SessionSocketHolder;
import com.myim.model.SingleMessageModel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import static com.myim.common.constant.TopicConstant.BUSINESS_2_IM_P2P_TOPIC;

/**
 * @author lx
 * @date 2024/04/11
 */
@Component
@RocketMQMessageListener(consumerGroup = "boot-consumer-group" + "${websocket.brokerId}",
        topic = BUSINESS_2_IM_P2P_TOPIC,
        selectorType = SelectorType.TAG,  // tag 过滤模式
        selectorExpression = "${websocket.brokerId}" // 消费 tagA 和 tagB
         )
@Slf4j
public class Business2ImMessageListener implements RocketMQListener<MessageExt> {




    @Override
    public void onMessage(MessageExt message) {

        String string = new String(message.getBody());
        SingleMessageModel singleMessageModel = JSONObject.parseObject(string, SingleMessageModel.class);
        String toUserId = singleMessageModel.getToUserId();
        log.info("Business2ImMessageListener receive message: {}", toUserId);

        NioSocketChannel nioSocketChannel = SessionSocketHolder.get(singleMessageModel.getAppId(), toUserId, singleMessageModel.getClientType(), singleMessageModel.getImei());
        if (nioSocketChannel != null) {
            nioSocketChannel.writeAndFlush(singleMessageModel);
        }


        log.info("Business2ImMessageListener receive message: {}", message);

    }
}
