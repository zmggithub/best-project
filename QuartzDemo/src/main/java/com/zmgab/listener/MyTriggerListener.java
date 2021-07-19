package com.zmgab.listener;

import org.quartz.*;

public class MyTriggerListener implements TriggerListener {

    public String name;

    public MyTriggerListener(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        // 可以提供构造方法，修改监听器名称
        //String name = this.getClass().getName();
        System.out.println("监听器名字：" + name);
        return name;
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {
        String name = trigger.getKey().getName();
        System.out.println(name + ",被触发");
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        String name = trigger.getKey().getName();
        // TriggerListener 给了一个选择取否决Job的执行，假如这个方法返回true，这个Job将不会为此Trigger触发而得到执行。
        System.out.println( name + ",没有被触发");
        return false;// true和false
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        String name = trigger.getKey().getName();
        System.out.println( name + ",错过触发");
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {
        String name = trigger.getKey().getName();
        // Trigger 被触发并且完成了Job的执行时，Scheduler调用这个方法。
        System.out.println(name + ",完成之后触发");
    }
}
