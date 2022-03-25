package com.zmgab.quartz;

import com.zmgab.demo.Log4jDemo;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

public class HelloSchedulerTrigger {

    private static Logger logger = Logger.getLogger(Log4jDemo.class);

    public static void main(String[] args) throws SchedulerException {
        Date start = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(start);
        System.out.println("打印开始时间：" + format);

        Date end = new Date();
        end.setTime(end.getTime() + 2000);

        // 1:调度器（Scheduler）,从工厂中获取调度的实例
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 2:任务实例(JobDetail)
        JobDetail build = JobBuilder.newJob(HelloTrigger.class)
                .withIdentity("job1", "group1")
                .usingJobData("adad","dsfsd")
                .build();// 任务名称，任务组

        start.setTime(start.getTime() + 2000);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 3:触发器 (Trigger)
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
//                .startNow()
                .startAt(start)
                .withSchedule(simpleSchedule().withIntervalInSeconds(3).repeatForever())
                // .endAt(end)
                .build();

        // 让调度器关联任务和触发器，保证按照触发器定义的条件执行任务
        scheduler.scheduleJob(build, trigger);
        scheduler.start();
    }
}
