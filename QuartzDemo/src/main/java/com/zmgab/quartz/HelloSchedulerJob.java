package com.zmgab.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class HelloSchedulerJob {

    public static void main(String[] args) throws SchedulerException {
        // 1:调度器（Scheduler）,从工厂中获取调度的实例
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 2:任务实例(JobDetail)
        JobDetail build = JobBuilder.newJob(HelloJob.class)
                .withIdentity("job1", "group1")
                .usingJobData("adad","dsfsd")
                .build();// 任务名称，任务组

        System.out.println("组 " + build.getKey().getGroup());
        System.out.println("名字 " + build.getKey().getName());
        System.out.println("类 " + build.getJobClass().getName());

        // 3:触发器 (Trigger)
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                // 如果遇到同名的key， Trigger中的.usingJobData() 会覆盖jobDetail中的.usingJobData()的值；
                .usingJobData("data","传递参数")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatSecondlyForever(5))
                .build();

        // 让调度器关联任务和触发器，保证按照触发器定义的条件执行任务
        scheduler.scheduleJob(build, trigger);

        scheduler.start();
    }
}
