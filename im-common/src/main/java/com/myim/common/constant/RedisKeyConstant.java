package com.myim.common.constant;

/**
 * @author lx
 * @date 2024/04/01
 */
public class RedisKeyConstant {


    public static final String LOGIN_PREFIX = "im-login";

    /**
     * 用户session，appId + UserSessionConstants + 用户id 例如10000：userSession：lld
     */
    public static final String USER_SESSION_CONSTANT = ":userSession:";
}
