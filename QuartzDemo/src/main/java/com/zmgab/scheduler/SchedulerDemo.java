package com.zmgab.scheduler;

import com.zmgab.quartz.HelloTrigger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SchedulerDemo {

    public static void main(String[] args) throws SchedulerException, InterruptedException {

        // 1:调度器（Scheduler）,从工厂中获取调度的实例
        StdSchedulerFactory ssf = new StdSchedulerFactory();
        Scheduler scheduler = ssf.getScheduler();

        // 2:任务实例(JobDetail)
        JobDetail build = JobBuilder.newJob(HelloTrigger.class)
                .build();// 任务名称，任务组

        // 3:触发器 (Trigger)
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("* * * * * ?"))
                .startNow()
                .build();

        // 让调度器关联任务和触发器，保证按照触发器定义的条件执行任务
        Date date = scheduler.scheduleJob(build, trigger);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("任务开始的时间：" + sdf.format(date));
        scheduler.start();

        // 挂起 Scheduler执行2秒后挂起
        Thread.sleep(2000L);
        scheduler.standby();
        Thread.sleep(5000L);

        // 重新启动任务
        scheduler.start();

        // 关闭 两种关闭方式
        // true 等待所有正在执行的job关闭之后，再关闭Scheduler，false直接关闭Scheduler
        scheduler.shutdown();
        // scheduler.shutdown(true);
        // scheduler.shutdown(false);
    }


}
