package com.zmgab.listener;

import org.quartz.*;

public class MySchedulerListener  implements SchedulerListener {
    @Override
    /**
     * 用于部署jobDetail
     */
    public void jobScheduled(Trigger trigger) {
        String name = trigger.getKey().getName();
        System.out.println(name + "完成部署");
    }

    @Override
    /**
     * 用于卸载jobDetail时调用
     */
    public void jobUnscheduled(TriggerKey triggerKey) {
        String name = triggerKey.getName();
        System.out.println(name + "完成卸载调用");
    }

    @Override
    /**
     * triggerFinalized方法：当一个Trigger来到了再也不会被触发的状态时调用这个方法。除非这个job已设置成了持久性，
     * 否则它就会从Scheduler中移除。
     */
    public void triggerFinalized(Trigger trigger) {
        String name = trigger.getKey().getName();
        System.out.println("触发器被移除" + name);
    }

    @Override
    /**
     * Scheduler调用这个方法时发生在一个Trigger或Trigger组被暂停时。假如是Trigger组的话，triggerName参数将为null
     */
    public void triggerPaused(TriggerKey triggerKey) {
        String name = triggerKey.getName();
        System.out.println("被暂停：" + name);
    }
    @Override
    public void triggersPaused(String s) {
        System.out.println("触发器组被暂停：" + s);
    }

    @Override
    /**
     * 调用这个方法是发生一个Trigger或Trigger组被暂停中恢复时，假如是Trigger组的话，triggerName参数将为null
     */
    public void triggerResumed(TriggerKey triggerKey) {
        String name = triggerKey.getName();
        System.out.println(name + "正在从暂停中恢复");
    }

    @Override
    public void triggersResumed(String s) {
        System.out.println("触发器组正在从暂停中恢复：" + s);
    }

    @Override
    public void jobAdded(JobDetail jobDetail) {
        System.out.println(jobDetail.getKey() + "添加工作任务！");
    }

    @Override
    public void jobDeleted(JobKey jobKey) {
        System.out.println(jobKey +  "删除工作任务！");
    }

    @Override
    /**
     * 当一个或一组jobDetail暂停时调用这个方法
     */
    public void jobPaused(JobKey jobKey) {
        System.out.println("工作任务正在被暂停");
    }

    @Override
    public void jobsPaused(String s) {
        System.out.println("工作组任务正在被暂停");
    }

    @Override
    /**
     * 当一个或一组job从暂停上恢复时调用这个方法。假如是一个job组，jobName参数将为null
     */
    public void jobResumed(JobKey jobKey) {
        System.out.println("工作任务正在被暂停上恢复");
    }

    @Override
    public void jobsResumed(String s) {
        System.out.println("工作组任务正在被暂停上恢复");
    }

    @Override
    /**
     * 在Scheduler的正常运行期间产生一个严重错误时调用这个方法。
     */
    public void schedulerError(String s, SchedulerException e) {
        System.out.println("期间产生一个严重错误时调用这个方法。" + e.getMessage());
        System.out.println(e.getUnderlyingException());
    }

    @Override
    /**
     * 当Scheduler处于StandBy模式时，调用该方法
     */
    public void schedulerInStandbyMode() {
        System.out.println("当Scheduler处于挂起模式时，调用该方法");
    }

    @Override
    /**
     * 在Scheduler开启时，调用该方法
     */
    public void schedulerStarted() {
        System.out.println("在Scheduler开启时，调用该方法");
    }

    @Override
    public void schedulerStarting() {
        System.out.println("在Scheduler正在开启时，调用该方法");
    }

    @Override
    /**
     * 当Scheduler停止时，调用该方法
     */
    public void schedulerShutdown() {
        System.out.println("当Scheduler停止时，调用该方法");
    }

    @Override
    public void schedulerShuttingdown() {
        System.out.println("当Scheduler正在被停止时，调用该方法");
    }

    @Override
    /**
     * 当Scheduler中的数据被清除时，调用该方法
     */
    public void schedulingDataCleared() {
        System.out.println("当Scheduler中的数据被清除时，调用该方法");
    }
}
