package com.zmgab.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

public class HelloSchedulerSimpleTrigger {


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
                // 如果有指定结束时间属性值，则结束时间属性优先于重复次数属性，这样的好处在于：当我们
                // 需要创建一个每间隔10秒触发一次直到指定结束时间的trigger，而无需计算从开始到结束的所重复的次数，我们
                // 只需简单的指定结束时间和使用PEPEAT_INDEFINITELY作为重复次数的属性值即可。
                .endAt(end)
                // 每5秒执行一次，重复时间不能为0； 执行3次就不执行了，起始值为0
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(5).withRepeatCount(4))
                .build();

        // 让调度器关联任务和触发器，保证按照触发器定义的条件执行任务
        scheduler.scheduleJob(build, trigger);
        scheduler.start();
    }
}
