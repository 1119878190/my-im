package com.myim.config;

import com.myim.server.IMServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BootstrapConfig {


    public static final  Integer BOSS_GROUP_THREAD_SIZE = 10;
    public static final  Integer WORK_GROUP_THREAD_SIZE = 10;

    @Value("${websocket.port:8000}")
    private Integer port;



    @PostConstruct
    public void initNettyServer(){
        IMServer imServer = new IMServer(port);
        imServer.start();


    }

}
