package com.myim.config;

import com.myim.server.IMServer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BootstrapConfig {


    public static final  Integer BOSS_GROUP_THREAD_SIZE = 10;
    public static final  Integer WORK_GROUP_THREAD_SIZE = 10;

    public static final Integer port = 8000;



    @PostConstruct
    public void initNettyServer(){

        // todo 后续port从nacos中读取
        IMServer imServer = new IMServer(8000);
        imServer.start();


    }

}
