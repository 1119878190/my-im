package com.myim.lbs.service;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.NacosNamingService;
import com.alibaba.nacos.shaded.com.google.gson.JsonObject;
import com.myim.lbs.response.ChooseWebsocketVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

/**
 * @author lx
 * @date 2024/03/26
 */
@Component
@Slf4j
public class RandomWebsocketLBSImpl implements ImWebsocketLBS {

    @Autowired
    private NacosNamingService namingService;

    @Value("${nacos.imwebsocket.servername}")
    private String serviceName;


    @Override
    public ChooseWebsocketVO choose() {
        try {
            List<Instance> allInstances = namingService.getAllInstances(serviceName);
            log.info("查询出来的实例为：{}", JSONObject.toJSONString(allInstances));

            Random random = new Random();
            int i = random.nextInt(allInstances.size());
            Instance instance = allInstances.get(i);
            String ip = instance.getIp();
            int port = instance.getPort();

            return new ChooseWebsocketVO(ip, port);

        } catch (NacosException e) {
            throw new RuntimeException(e);
        }

    }
}
