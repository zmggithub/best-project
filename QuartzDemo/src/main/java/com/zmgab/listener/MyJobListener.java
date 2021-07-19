package com.zmgab.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class MyJobListener implements JobListener {
    @Override
    public String getName() {
        String name = this.getClass().getName();
        System.out.println("监听器名字：" + name);
        return name;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        String name = jobExecutionContext.getJobDetail().getKey().getName();
        System.out.println("Scheduler在JobDetail将要被执行时调用的方法 + " + name);
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
        String name = jobExecutionContext.getJobDetail().getKey().getName();
        System.out.println("Scheduler在JobDetail将要被执行,但又被TriggerListener否决时会调用该方法： + " + name);
    }

    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        String name = jobExecutionContext.getJobDetail().getKey().getName();
        System.out.println("Scheduler在JobDetail被执行之后调用的方法 + " + name);
    }
}
