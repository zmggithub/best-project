package com.nettydemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.ArrayList;
import java.util.List;

import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @Author zmgab@foxmail.com
 * @Date 2023/2/27 18:25
 */
public class MessageHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    /**
     * 本方法用于读取客户端发送的信息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    @Trace
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf msgByteBuf = (ByteBuf) msg;
        logger.info(msgByteBuf.toString());
        byte[] msgBytes = new byte[msgByteBuf.readableBytes()];
        // msg中存储的是ByteBuf类型的数据，把数据读取到byte[]中
        msgByteBuf.readBytes(msgBytes);
        // 释放资源
        msgByteBuf.release();

        // 可能返回到的msgByteBuf是多条信息拼起来的,把他们拆开分别处理
        List<byte[]> list = getMsgList(msgBytes);

        // 真正处理信息的方法
        list.forEach(v -> handler(v, ctx));

        logger.info("进入参数： clientRequest");
        String url = "http://localhost:9527/clientRequest";
        HttpEntity<Object> request = new HttpEntity<>(null,null);


        ResponseEntity<String> exchange = new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);
        logger.info("返回结果：" + exchange);

    }

    /**
     * 切分信息的方法
     *
     * @param msgBytes
     * @return
     */
    private List<byte[]> getMsgList(byte[] msgBytes) {
        List<byte[]> list = new ArrayList<byte[]>();
        //具体业务代码略
        return list;
    }

    /**
     * 本方法用作处理异常
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause.getClass() == io.netty.handler.timeout.ReadTimeoutException.class) {
            logger.info("来自" + NettyServer.sc.remoteAddress() + "的连接超时断开");
        } else {
            cause.printStackTrace();
            logger.info("来自" + NettyServer.sc.remoteAddress() + "的连接异常断开");
            ctx.close();
        }
        NettyServer.sc= null;
    }

    /**
     * 信息获取完毕后操作
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 断开连接时操作
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {

        if (NettyServer.sc!= null) {
            logger.info("来自" + NettyServer.sc.remoteAddress() + "的连接主动断开");
            NettyServer.sc= null;
        }

        ctx.fireChannelUnregistered();
    }

    /**
     * 根据信息具体操作的业务方法
     *
     * @param msgBytes
     * @param ctx
     */
    private void handler(byte[] msgBytes, ChannelHandlerContext ctx) {


    }


}
