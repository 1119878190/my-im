package com.myim.factory;

import com.google.common.util.concurrent.AtomicDouble;
import com.myim.common.enums.CommandEnum;
import com.myim.server.impl.LoginMessageHandlerService;
import com.myim.server.MessageHandlerService;
import com.myim.server.impl.SingleMessageHandlerService;

import javax.annotation.Resource;

/**
 * @author lx
 * @date 2024/03/29
 */
public class MessageHandlerServiceFactory {

    @Resource
    private LoginMessageHandlerService loginMessageHandlerService;
    @Resource
    private SingleMessageHandlerService singleMessageHandlerService;


    /**
     * 获取消息处理类
     *
     * @param commandEnum 指令枚举
     */
    public MessageHandlerService getMessageHandler(CommandEnum commandEnum) {

        // todo 暂时使用工厂模式  switch判断，后优化为放入map缓存中，根据key直接获取
        switch (commandEnum) {
            case LOGIN:
                return loginMessageHandlerService;
            case SEND_SINGLE_MESSAGE:
                return singleMessageHandlerService;
            default:
                return null;

        }
    }


}
