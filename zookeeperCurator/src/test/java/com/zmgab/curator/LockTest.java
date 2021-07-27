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

/**
 * @author: zmg
 */
public class LockTest {

    public static void main(String[] args) {

        Ticket12306 ticket = new Ticket12306();

        // 创建客户端
        Thread t1 = new Thread(ticket, "携程");
        Thread t2 = new Thread(ticket, "飞猪");

        t1.start();
        t2.start();

    }

}



















































