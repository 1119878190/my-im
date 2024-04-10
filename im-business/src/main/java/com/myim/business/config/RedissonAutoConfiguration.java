package com.myim.business.config;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(Config.class)
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfiguration {

    @Autowired
    RedissonProperties redissonProperties;

    /**
     * 单机模式自动装配
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "spring.redisson.address")
    Redisson redissonSingle() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer().setAddress(redissonProperties.getAddress());

        if (StringUtils.isNotBlank(redissonProperties.getPassword())) {
            serverConfig.setPassword(redissonProperties.getPassword());
        }
        // 增加redissonProperties中未设置的配置
        serverConfig.setConnectionPoolSize(redissonProperties.getConnectionPoolSize());
        serverConfig.setConnectionMinimumIdleSize(redissonProperties.getConnectionMinimumIdleSize());
        serverConfig.setConnectTimeout(redissonProperties.getConnectTimeout());
        serverConfig.setTimeout(redissonProperties.getTimeout());
        return (Redisson)Redisson.create(config);
    }
}