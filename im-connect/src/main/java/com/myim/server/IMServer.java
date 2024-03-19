package com.myim.server;

import com.myim.initializer.MyNettyServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.myim.config.BootstrapConfig.BOSS_GROUP_THREAD_SIZE;
import static com.myim.config.BootstrapConfig.WORK_GROUP_THREAD_SIZE;

public class IMServer {


    private static final Logger logger = LoggerFactory.getLogger(IMServer.class);

    EventLoopGroup bossGroup;
    EventLoopGroup workerGroup;
    ServerBootstrap serverBootstrap;


    public IMServer() {
        bossGroup = new NioEventLoopGroup(BOSS_GROUP_THREAD_SIZE);
        workerGroup = new NioEventLoopGroup(WORK_GROUP_THREAD_SIZE);
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                // 服务端可连接队列大小
                .option(ChannelOption.SO_BACKLOG, 10240)
                // 参数表示允许重复使用本地地址和端口
                .option(ChannelOption.SO_REUSEADDR, true)
                // 是否禁用Nagle算法 简单点说是否批量发送数据 true关闭 false开启。 开启的话可以减少一定的网络开销，但影响消息实时性
                .childOption(ChannelOption.TCP_NODELAY, true)
                // 保活开关2h没有数据服务端会发送心跳包
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new MyNettyServerInitializer());

        logger.info("imServer 初始化完成");
    }

    public void start()  {
        try {
            this.serverBootstrap.bind(8000).sync();
            logger.info("imServer 启动成功port");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
