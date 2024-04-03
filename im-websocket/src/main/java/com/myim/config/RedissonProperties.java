package com.myim.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.redisson")
public class RedissonProperties {
    /**
     * redis单机地址，格式：redis://127.0.0.1:6379
     */
    private String address;

    /**
     * redis登录密码
     */
    private String password;

    /**
     * 连接超时，单位：毫秒
     */
    private int connectTimeout = 10000;

    /**
     * 命令等待超时，单位：毫秒
     */
    private int timeout = 15000;

    /**
     * 连接池最大容量。连接池的连接数量自动弹性伸缩
     */
    private int connectionPoolSize = 32;

    /**
     * 最小保持连接数（长连接）。长期保持一定数量的连接有利于提高瞬时写入反应速度
     */
    private int connectionMinimumIdleSize = 16;


}