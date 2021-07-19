package com.zmgab.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

public class HelloSchedulerTrigger {


    public static void main(String[] args) throws SchedulerException {
        Date start = new Date();
        start.setTime(start.getTime() + 2000);

        Date end = new Date();
        end.setTime(end.getTime() + 2000);

        // 1:调度器（Scheduler）,从工厂中获取调度的实例
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 2:任务实例(JobDetail)
        JobDetail build = JobBuilder.newJob(HelloTrigger.class)
                .withIdentity("job1", "group1")
                .usingJobData("adad","dsfsd")
                .build();// 任务名称，任务组


        // 3:触发器 (Trigger)
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
//                .startNow()
                .startAt(start)
                .endAt(end)
                .build();

        // 让调度器关联任务和触发器，保证按照触发器定义的条件执行任务
        scheduler.scheduleJob(build, trigger);
        scheduler.start();
    }
}
