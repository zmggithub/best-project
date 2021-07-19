package com.zmgab.listener;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

public class SchedulerListenerDemo {

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        // 1:调度器（Scheduler）,从工厂中获取调度的实例
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 2:任务实例(JobDetail)
        JobDetail build = JobBuilder.newJob(ListenerHello.class)
                .withIdentity("job1", "group1")
                .build();

        // 3:触发器 (Trigger)
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1).withRepeatCount(3))
                .startNow()
                .build();

        // 让调度器关联任务和触发器，保证按照触发器定义的条件执行任务
        scheduler.scheduleJob(build, trigger);

        // 创建并注册全局trigger
        scheduler.getListenerManager().addSchedulerListener(new MySchedulerListener());

        scheduler.getListenerManager().removeSchedulerListener(new MySchedulerListener());

        scheduler.start();

        Thread.sleep(7000);
        scheduler.shutdown();
    }
}
