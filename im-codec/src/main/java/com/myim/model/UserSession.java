package com.myim.model;

import lombok.Data;

@Data
public class UserSession {

    private String userId;

    /**
     * 应用ID
     */
    private Integer appId;

    /**
     * 端的标识
     */
    private Integer clientType;

    //sdk 版本号
    private Integer version;

    //连接状态 1=在线 2=离线
    private Integer connectState;

    /**
     * 用户连接的哪个im服务
     * im 服务id
     */
    private String brokerId;

    /**
     * im服务 ip
     */
    private String brokerHost;

    /**
     *im服务 端口
     */
    private String brokerPort;

    private String imei;

}
