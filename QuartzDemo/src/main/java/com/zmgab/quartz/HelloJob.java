package com.zmgab.quartz;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

// 有无状态的注解 多次调用Job的时候，会对Job进行持久化，即保存一个数据的信息
// @PersistJobDataAfterExecution
public class HelloJob implements Job {
    public HelloJob() {
        System.out.println("Hello job construct");
    }

    String message;

    public void setMessage(String message) {
        this.message = message;
    }


    Integer count;

    public void setCount(Integer count) {
        this.count = count;
    }
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(date);
        System.out.println("打印调度时间：" + format);

        JobKey jobDetail = jobExecutionContext.getJobDetail().getKey();
        Trigger trigger = jobExecutionContext.getTrigger();
        JobDataMap map = jobExecutionContext.getMergedJobDataMap();
        Object data = map.get("data");
        System.out.println(data);


        jobExecutionContext.getFireTime();
        jobExecutionContext.getNextFireTime();
    }
}
