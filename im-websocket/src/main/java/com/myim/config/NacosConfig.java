package com.myim.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author lx
 * @date 2024/03/28
 */
@Configuration
public class NacosConfig {

    @Value("${websocket.port:8000}")
    private String websocketPort;

    /**
     * 创建 NacosDiscoveryProperties bean对象，并自定义元数据
     *
     * @return
     */
    @Bean
    public NacosDiscoveryProperties nacosProperties() {
        NacosDiscoveryProperties nacosDiscoveryProperties = new NacosDiscoveryProperties();
        Map<String, String> metadata = nacosDiscoveryProperties.getMetadata();
        metadata.put("startup.time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date()));
        metadata.put("websocketPort", "8000");
        return nacosDiscoveryProperties;
    }
}
