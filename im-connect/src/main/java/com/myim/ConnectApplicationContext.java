package com.myim;

import com.myim.server.IMServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lx
 * @date 2024/03/19
 */
@SpringBootApplication
public class ConnectApplicationContext {


    public static void main(String[] args) {
        SpringApplication.run(ConnectApplicationContext.class, args);

        IMServer imServer = new IMServer();
        imServer.start();
    }
}
