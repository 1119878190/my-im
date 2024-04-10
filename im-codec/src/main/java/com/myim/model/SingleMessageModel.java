package com.myim.model;

import lombok.Data;

import java.util.Date;

/**
 * @author lx
 * @date 2024/04/09
 */
@Data
public class SingleMessageModel {

    /**
     * id，保证全局唯一
     */
    private String id;

    /**
     * 发送者
     */
    private String fromUserId;

    /**
     * 接收者
     */
    private String toUserId;

    /**
     * 消息id  保证局部唯一(单聊消息AB两人局部唯一递增 ，可通过redis incr 实现)
     */
    private String messageSequence;

    /**
     * 消息类型
     */
    private String messageType;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 消息状态
     */
    private String messageStatus;

    private String messageContent;

}
