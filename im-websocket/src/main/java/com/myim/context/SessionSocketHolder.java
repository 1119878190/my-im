package com.myim.context;

import com.myim.common.model.session.UserClientDTO;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * channel存储工具类,存储用户与channel的关联关系
 *
 * @author lx
 * @date 2024/04/02
 */
public class SessionSocketHolder {

    private static final Map<UserClientDTO, NioSocketChannel> CHANNELS = new ConcurrentHashMap<>();


    public static void put(Integer appId, String userId, Integer clientType, String imei, NioSocketChannel channel) {
        UserClientDTO dto = new UserClientDTO();
        dto.setAppId(appId);
        dto.setClientType(clientType);
        dto.setUserId(userId);
        dto.setImei(imei);
        CHANNELS.put(dto, channel);
    }

    public static NioSocketChannel get(Integer appId, String userId,
                                       Integer clientType, String imei) {
        UserClientDTO dto = new UserClientDTO();
        dto.setAppId(appId);
        dto.setClientType(clientType);
        dto.setUserId(userId);
        dto.setImei(imei);
        return CHANNELS.get(dto);
    }


    public static void remove(Integer appId, String userId, Integer clientType, String imei) {
        UserClientDTO dto = new UserClientDTO();
        dto.setAppId(appId);
        dto.setClientType(clientType);
        dto.setUserId(userId);
        dto.setImei(imei);
        CHANNELS.remove(dto);
    }

    public static void remove(NioSocketChannel channel) {
        CHANNELS.entrySet().stream().filter(entity -> entity.getValue() == channel)
                .forEach(entry -> CHANNELS.remove(entry.getKey()));
    }


}
