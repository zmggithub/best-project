package com.zmgab.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author: zmg
 */
public class CuratorWatcherTest {

    CuratorFramework client;

    /**
     * 可以监控整个树上的所有节点，类似前两个
     * 监听某个节点自己和所有子节点们
     * @throws Exception
     */
    @Test
    public void testTreeCache() throws Exception {
        // 1.创建NodeCache
        final TreeCache cache = new TreeCache(client, "/app2");
        // 2.绑定监听
        cache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                System.out.println("节点变化了");
            }

        });
        // 3.开启监听
        cache.start();
        while (true) {

        }
    }

    /**
     * 监控一个ZNode的子节点
     * @throws Exception
     */
    @Test
    public void testPathChildrenCache() throws Exception {
        // 1.创建NodeCache
        final PathChildrenCache cache = new PathChildrenCache(client, "/app2",true);
        // 2.绑定监听
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                System.out.println("节点变化了");
                System.out.println(pathChildrenCacheEvent);
                // 监听子节点的数据变更，并且拿到变更后的数据
                PathChildrenCacheEvent.Type type = pathChildrenCacheEvent.getType();
                if (type.equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)) {
                    System.out.println("数据变了");
                    byte[] data = pathChildrenCacheEvent.getData().getData();
                    System.out.println(new String(data));

                }

            }
        });
        // 3.开启监听
        cache.start();
        while (true) {

        }
    }

    /**
     * 只是监听某一个特定的节点
     * @throws Exception
     */
    @Test
    public void testNodeCache() throws Exception {
        // 1.创建NodeCache
        final NodeCache nodeCache = new NodeCache(client, "/app1");
        // 2.注册监听
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("节点变代了~");

                // 获取修改节点后的数据
                byte[] data = nodeCache.getCurrentData().getData();
                System.out.println(new String(data));

            }
        });
        // 3.开启监听
        nodeCache.start(true);
        while (true) {

        }
    }














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

    @After
    public void afterTest() {
        if (client != null) {
            client.close();
        }
    }
}
