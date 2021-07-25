package com.zmgab.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author: zmg
 */
public class CuratorTest {

    CuratorFramework client;

    @Before
    public void testConnect() {
        /**
         * Create a new client
         *
         * @param connectString       list of servers to connect to 连接字符串。
         * @param sessionTimeoutMs    session timeout 会话超时时间 单位ms
         * @param connectionTimeoutMs connection timeout 连接超时时间 单位ms
         * @param retryPolicy         retry policy to use 重试策略
         * @return client
         */
        ExponentialBackoffRetry retry = new ExponentialBackoffRetry(3000, 10);
        // 第一种方式
        client = CuratorFrameworkFactory.newClient(
                "192.168.1.189:1281", 60 * 1000, 15 * 1000, retry);

        // 第二种方式
        // namespace 默认根节点
        client = CuratorFrameworkFactory.builder().connectString("192.168.1.189:1281")
                .sessionTimeoutMs(60 * 1000)
                .retryPolicy(retry).namespace("zmgab").build();

        // 开启连接
        client.start();
    }


    /**
     * 创建节点： create 持久 临时 顺序 数据
     * 1. 基本创建
     * 2. 创建节点 带有数据
     * 3. 设置节点的类型
     * 4. 创建多级节点
     */
    @Test
    public void testCreate() throws Exception {
        // 1.基本创建 如果创建节点没有指定数据，则默认将客户端的ip作为数据存储
        String path = client.create().forPath("/app1");
        // path = client.create().forPath("/app1", "有数据".getBytes());
        System.out.println(path);

    }


    @After
    public void afterTest() {
        if (client != null) {
            client.close();
        }
    }
}
