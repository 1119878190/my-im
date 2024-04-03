package com.myim.server.impl;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.fastjson2.JSONObject;
import com.myim.common.constant.ChannelConstant;
import com.myim.common.enums.ImConnectStatusEnum;
import com.myim.common.model.session.UserClientDTO;
import com.myim.context.SessionSocketHolder;
import com.myim.model.LoginRequest;
import com.myim.model.UserSession;
import com.myim.proto.Message;
import com.myim.proto.MessageHeader;
import com.myim.server.MessageHandlerService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.myim.common.constant.RedisKeyConstant.USER_SESSION_CONSTANT;

/**
 * 登录消息处理器
 *
 * @author lx
 * @date 2024/03/29
 */
@Component
public class LoginMessageHandlerService implements MessageHandlerService {


    @Autowired
    private Redisson redisson;
    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    /**
     *
     * 1.填充 netty channel 用户信息
     *
     * 2. 构建 redis路由层，并把用户信息存入map结构中
     *     2.1 map结构： appId:userSession:usrId    clientType:imei   usrSession对象， key的设计思路  clientType:imei 可作为唯一登录标识 可适配多端登录，因为imei号不一样
     *
     * 3.存储 user 跟 channel 的关系,用户后续精确找到用户推送消息
     *
     * 4. 多端登录，通过redis的发布订阅，通知其它服务上的端进行退出
     *
     * @param ctx channel 上下文
     * @param msg 消息
     */
    @Override
    public void messageReceiveHandler(ChannelHandlerContext ctx, Message msg) {


        MessageHeader messageHeader = msg.getMessageHeader();
        Object messagePack = msg.getMessagePack();
        LoginRequest loginRequest = JSONObject.parseObject(JSONObject.toJSONString(messagePack), LoginRequest.class);

        // 填充 channel 用户信息
        ctx.channel().attr(AttributeKey.valueOf(ChannelConstant.USERID)).set(loginRequest.getAccount());
        ctx.channel().attr(AttributeKey.valueOf(ChannelConstant.APPID)).set(msg.getMessageHeader().getAppId());
        ctx.channel().attr(AttributeKey.valueOf(ChannelConstant.CLIENT_TYPE)).set(msg.getMessageHeader().getClientType());
        ctx.channel().attr(AttributeKey.valueOf(ChannelConstant.IMEI)).set(msg.getMessageHeader().getImei());

        // 将用户信息存储到 redis
        UserSession userSession = new UserSession();
        userSession.setUserId(loginRequest.getAccount());
        userSession.setAppId(msg.getMessageHeader().getAppId());
        userSession.setClientType(msg.getMessageHeader().getClientType());
        userSession.setConnectState(ImConnectStatusEnum.ONLINE_STATUS.getCode());
        // todo 需要获取nacos 上当前注册的服务的brokerId ,可以将brokerId 写到配置文件中
        userSession.setBrokerId("");
        userSession.setImei(msg.getMessageHeader().getImei());
        String hostAddress = nacosDiscoveryProperties.getIp();
        userSession.setBrokerHost(hostAddress);
        RMap<String, String> map = redisson.getMap(msg.getMessageHeader().getAppId() + USER_SESSION_CONSTANT + loginRequest.getAccount());
        map.put(String.valueOf(messageHeader.getClientType()) + ":" + msg.getMessageHeader().getImei(),
                JSONObject.toJSONString(userSession));


        // 存储 user 跟 channel 的关系
        UserClientDTO userClientDTO = new UserClientDTO();
        userClientDTO.setAppId(messageHeader.getAppId());
        userClientDTO.setClientType(messageHeader.getClientType());
        userClientDTO.setUserId(loginRequest.getAccount());
        userClientDTO.setImei(messageHeader.getImei());
        SessionSocketHolder.put(messageHeader.getAppId(), loginRequest.getAccount(),
                messageHeader.getClientType(), messageHeader.getImei(), (NioSocketChannel) ctx.channel());



        // todo  多端登录，通过redis的发布订阅，通知其它服务上的端进行退出


    }
}
