package com.nettydemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 *
 * @Author zmgab@foxmail.com
 * @Date 2023/2/27 18:23
 */
public class NettyServerThread extends Thread {

    private static Logger logger = LoggerFactory.getLogger(NettyServerThread.class);

    @Override
    public void run() {

        EventLoopGroup acceptor = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        NettyServer.acceptor = acceptor;
        NettyServer.worker = worker;
        ServerBootstrap bootstrap = new ServerBootstrap();

        // 添加boss和worker组
        bootstrap.group(acceptor, worker);
        //这句是指定允许等待accept的最大连接数量,我只需要连一个客户端,这里就关掉了,java默认是50个
        // bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        // 用于构造socketchannel工厂
        bootstrap.channel(NioServerSocketChannel.class);

        /**
         * 传入自定义客户端Handle（处理消息）
         */
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                if (NettyServer.sc == null) {
                    logger.info("来自" + ch.remoteAddress() + "的新连接接入");
                    NettyServer.sc= ch;
                    // 注册handler
                    ch.pipeline().addLast(new ReadTimeoutHandler(10));
                    ch.pipeline().addLast(new MessageHandler());
                } else {
                    ch.close();
                }
            }
        });

        // 绑定端口，开始接收进来的连接
        ChannelFuture f;
        try {
            f = bootstrap.bind(8888).sync();
            // 等待服务器 socket 关闭 。
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}