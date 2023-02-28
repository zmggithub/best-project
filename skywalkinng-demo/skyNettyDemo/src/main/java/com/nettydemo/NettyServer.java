package com.nettydemo;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.xml.ws.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @Author zmgab@foxmail.com
 * @Date 2023/2/27 18:01
 */
@Component
public class NettyServer {

    private static Logger logger = LoggerFactory.getLogger(NettyServer.class);

    // 保存response的map
    public static Map<String, Response> map = new HashMap<String, Response>();
    // 保存客户端连接的通道引用
    public static SocketChannel sc = null;

    public static EventLoopGroup acceptor;
    public static EventLoopGroup worker;

    @PostConstruct
    public void init() throws InterruptedException {
        new NettyServerThread().start();
        logger.info("nettyServer启动");
    }

    @PreDestroy
    public void exit() {
        acceptor.shutdownGracefully();
        worker.shutdownGracefully();
    }

}