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

    // private int port = 12255;
    //
    // @PostConstruct
    // public void start() throws InterruptedException {
    //     EventLoopGroup bossGroup = new NioEventLoopGroup();
    //     EventLoopGroup workerGroup = new NioEventLoopGroup();
    //     try {
    //         ServerBootstrap serverBootstrap = new ServerBootstrap();
    //         serverBootstrap.group(bossGroup, workerGroup)
    //                 .channel(NioServerSocketChannel.class)
    //                 .childHandler(new ChannelInitializer<SocketChannel>() {
    //                     @Override
    //                     protected void initChannel(SocketChannel ch) throws Exception {
    //                         ChannelPipeline pipeline = ch.pipeline();
    //                         pipeline.addLast(new StringDecoder());
    //                         pipeline.addLast(new IdleStateHandler(5, 0, 0));
    //                         pipeline.addLast(new MessageHandler());
    //                     }
    //                 });
    //
    //         ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
    //         channelFuture.channel().closeFuture().sync();
    //     } finally {
    //         bossGroup.shutdownGracefully();
    //         workerGroup.shutdownGracefully();
    //     }
    // }

}