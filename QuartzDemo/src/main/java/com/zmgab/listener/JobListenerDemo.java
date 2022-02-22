package com.zmgab.listener;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

public class JobListenerDemo {

    public static void main(String[] args) throws SchedulerException {
        // 1:调度器（Scheduler）,从工厂中获取调度的实例
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 2:任务实例(JobDetail)
        JobDetail build = JobBuilder.newJob(ListenerHello.class)
                .withIdentity("job2", "group1")
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

        // 创建并注册一个全局的JobListener
        // scheduler.getListenerManager().addJobListener(new MyJobListener(), EverythingMatcher.allJobs());
        // 创建并注册一个局部的JobListener
        MyJobListener myJobListener = new MyJobListener();
        JobKey jobKey = JobKey.jobKey("job2", "group1");
        KeyMatcher<JobKey> jobKeyKeyMatcher = KeyMatcher.keyEquals(jobKey);
        scheduler.getListenerManager().addJobListener(myJobListener,jobKeyKeyMatcher);

        scheduler.start();
    }
}
