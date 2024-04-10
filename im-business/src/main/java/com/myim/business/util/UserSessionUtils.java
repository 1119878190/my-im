package com.myim.business.util;


import com.alibaba.fastjson.JSONObject;
import com.myim.common.enums.ImConnectStatusEnum;
import com.myim.model.UserSession;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.myim.common.constant.RedisKeyConstant.USER_SESSION_CONSTANT;

/**
 * 用户会话工具类
 *
 * @author lx
 */
@Component
public class UserSessionUtils {

    @Autowired
    private Redisson redisson;


    /**
     * 获取用户所有的在线session
     *
     * @param appId  应用程序id
     * @param userId 用户id
     * @return {@link List}<{@link UserSession}>
     */
    public List<UserSession> getUserAllOnlineSession(Integer appId, String userId) {
        String userSessionKey = appId + USER_SESSION_CONSTANT + userId;
        Map<Object, Object> entries = redisson.getMap(userSessionKey);
        List<UserSession> list = new ArrayList<>();
        Collection<Object> values = entries.values();
        for (Object value : values) {
            String str = (String) value;
            UserSession userSession = JSONObject.parseObject(str, UserSession.class);
            if (userSession.getConnectState().equals(ImConnectStatusEnum.ONLINE_STATUS.getCode())) {
                list.add(userSession);
            }

        }
        return list;

    }


    /**
     * 获取指定端的用户session
     *
     * @param appId  应用程序id
     * @param userId 用户id
     * @return {@link List}<{@link UserSession}>
     */
    public UserSession getUserSession(Integer appId, String userId, Integer clientType, String imei) {
        String userSessionKey = appId + USER_SESSION_CONSTANT + userId;
        String hashKey = clientType + ":" + imei;
        RMap<Object, Object> map = redisson.getMap(userSessionKey);
        Object o = map.get(hashKey);
        UserSession userSession = JSONObject.parseObject(o.toString(), UserSession.class);
        return userSession;

    }


}
