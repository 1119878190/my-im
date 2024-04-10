package com.myim.business.mq;


import com.alibaba.fastjson2.JSONObject;
import com.myim.business.util.UserSessionUtils;
import com.myim.model.SingleMessageModel;
import com.myim.model.UserSession;
import com.myim.proto.Message;
import com.myim.proto.MessageHeader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

import static com.myim.common.constant.TopicConstant.BUSINESS_2_IM_P2P_TOPIC;
import static com.myim.common.constant.TopicConstant.IM_2_BUSINESS_P2P_TOPIC;

/**
 * 单聊消息 mq 接收者
 *
 * @author lx
 * @date 2024/04/10
 */
@Component
@RocketMQMessageListener(consumerGroup = "boot-consumer-group", topic = IM_2_BUSINESS_P2P_TOPIC)
@Slf4j
public class SingleMessageListener implements RocketMQListener<MessageExt> {


    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private UserSessionUtils userSessionUtils;

    /**
     * 思路：
     * 1.将消息持久化
     * 2.同步消息到发送方其他端
     * 3.发送消息到接收方所在的im，然后im进行消息推送
     *
     * @param messageExt
     */
    @Override
    public void onMessage(MessageExt messageExt) {
        log.info("单聊消息 business mq  收到消息");
        String string = new String(messageExt.getBody());

        Message message = JSONObject.parseObject(string, Message.class);
        MessageHeader messageHeader = message.getMessageHeader();
        Object messagePack = message.getMessagePack();
        SingleMessageModel singleMessageModel = JSONObject.parseObject(JSONObject.toJSONString(messagePack), SingleMessageModel.class);
        String fromUserId = singleMessageModel.getFromUserId();
        String toUserId = singleMessageModel.getToUserId();

        // todo 消息持久化


        // TODO: 同步消息到发送方其他端


        // TODO: 设置消息id

        // 获取接收者在线端 (通过之前构建的redis路由层获取)
        // map结构： appId:userSession:usrId    clientType:imei   usrSession对象
        List<UserSession> userAllOnlineSession = userSessionUtils.getUserAllOnlineSession(messageHeader.getAppId(), fromUserId);
        if (CollectionUtils.isNotEmpty(userAllOnlineSession)) {
            for (UserSession userSession : userAllOnlineSession) {

                rocketMQTemplate.asyncSend(userSession.getBrokerId() + "_" + BUSINESS_2_IM_P2P_TOPIC,
                        JSONObject.toJSONString(singleMessageModel), new SendCallback() {
                            @Override
                            public void onSuccess(SendResult sendResult) {

                            }

                            @Override
                            public void onException(Throwable throwable) {

                            }
                        });

            }

        }

    }
}
