package com.zmgab.listener;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

public class TriggerListenerDemo {
    
    public static void main(String[] args) throws SchedulerException {
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
                .usingJobData("data","传递参数")
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1).withRepeatCount(3))
                .startNow()
                .build();

        // 让调度器关联任务和触发器，保证按照触发器定义的条件执行任务
        scheduler.scheduleJob(build, trigger);

        // 创建并注册全局trigger
        // scheduler.getListenerManager().addTriggerListener(new MyTriggerListener("simpleListener"),EverythingMatcher.allTriggers());
        // 创建并注册局部trigger
        scheduler.getListenerManager().addTriggerListener(new MyTriggerListener("simpleListener"),KeyMatcher.keyEquals(TriggerKey.triggerKey("trigger1", "group1")));

        scheduler.start();
    }
}
