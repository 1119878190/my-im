package com.myim.lbs.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.client.naming.NacosNamingService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class NacosNamingServiceConfig {

    private final NacosDiscoveryProperties nacosDiscoveryProperties;

    @Bean
    public NacosNamingService nacosNamingService() {
        try {
            return new NacosNamingService(nacosDiscoveryProperties.getNacosProperties());
        } catch (NacosException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
