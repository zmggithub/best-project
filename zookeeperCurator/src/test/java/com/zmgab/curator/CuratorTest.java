package com.zmgab.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
                "192.168.153.190:2181", 60 * 1000, 15 * 1000, retry);

        // 第二种方式
        // namespace 默认根节点
        client = CuratorFrameworkFactory.builder().connectString("192.168.153.190:2181")
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
        // 1.基本创建
//        String path = client.create().forPath("/app1");

        // 2.创建节点 带有数据  如果创建节点没有指定数据，则默认将客户端的ip作为数据存储
//        String path = client.create().forPath("/app2", "有数据".getBytes());

        // 3.设置节点的类型 默认类型：持久化
//        String path = client.create().withMode(CreateMode.EPHEMERAL).forPath("/app3");

        // 4. 创建多级节点  app4/demo
        String path = client.create().creatingParentsIfNeeded().forPath("/app4/demo","data".getBytes());

        System.out.println(path);
    }


    /**
     * 1.查询数据：get
     * 2.查询子节点：ls
     * 3.查询节点状态信息： ls -s
     */
    @Test
    public void testCet() throws Exception {
        // 1.查询数据：get
//        byte[] bytes = client.getData().forPath("/app1");
//        System.out.println(new String(bytes));


        // 2.查询子节点：ls  这个/ 代表是namespace中设置的数据
//        List<String> strings = client.getChildren().forPath("/");
//        strings.forEach(System.out::println);

        // 3.查询节点状态信息： ls -s 状态会放在stat对象中
        Stat stat = new Stat();
        System.out.println(stat);
        byte[] bytes = client.getData().storingStatIn(stat).forPath("/app4");
        System.out.println(new String(bytes));
        System.out.println(stat);
    }





    /**
     * 1.修改数据
     */
    @Test
    public void testSet() throws Exception {
        client.setData().forPath("/app4", "haha".getBytes());
    }
    /**
     * 1.修改数据 根据版本号修改，避免线程问题
     */
    @Test
    public void testSetForVersion() throws Exception {
        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath("/app4");
        int version = stat.getVersion();
        client.setData().withVersion(version).forPath("/app4", "hahaha".getBytes());
    }


    /**
     * 删除节点： delete deleteall
     * 1.删除单个迎战
     * 2.删除带有子节点的节点
     * 3.必须成功的删除
     * 4.回调
     * @throws Exception
     */
    @Test
    public void testDelete() throws Exception {
//        client.delete().forPath("/app1");
//        client.delete().deletingChildrenIfNeeded().forPath("/app4");
//        client.delete().guaranteed().forPath("/app2");

        client.delete().guaranteed().inBackground(new BackgroundCallback(){

            @Override
            public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                System.out.println(curatorFramework);
                System.out.println(curatorEvent);
                System.out.println("我被删除了");
            }
        }).forPath("/app2");
    }



    @After
    public void afterTest() {
        if (client != null) {
            client.close();
        }
    }
}
