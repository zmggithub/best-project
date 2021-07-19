package com.zmgab.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloTrigger implements Job{

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(date);
        System.out.println("打印调度时间：" + format);


        jobExecutionContext.getTrigger().getStartTime();
        jobExecutionContext.getTrigger().getEndTime();
        jobExecutionContext.getTrigger().getKey();
        jobExecutionContext.getTrigger().getJobDataMap();
        jobExecutionContext.getTrigger().getJobKey();
    }
}
