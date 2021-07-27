package com.zmgab.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

public class Ticket12306 implements Runnable {

    private int tickets = 10;

    private InterProcessMutex lock;

    public Ticket12306() {
        ExponentialBackoffRetry retry = new ExponentialBackoffRetry(3000, 10);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("192.168.153.190:2181")
                .sessionTimeoutMs(60 * 1000)
                .retryPolicy(retry).namespace("zmgab").build();
        client.start();

        lock = new InterProcessMutex(client, "lock");
    }

    @Override
    public void run() {

        try {
            lock.acquire(3, TimeUnit.SECONDS);
            while (true) {
                if (tickets > 0) {
                    System.out.println(Thread.currentThread() + ":" + tickets);
                    tickets--;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                lock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
