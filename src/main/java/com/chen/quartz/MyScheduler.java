package com.chen.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Description: <br>调度器
 * @Date: Created in 2020/1/3 <br>
 * @Author: chenjianwen
 */
public class MyScheduler {

    public static void main(String[] args) throws SchedulerException, InterruptedException {

        Date startDate = new Date();
        startDate.setTime(startDate.getTime() + 6000);
        Date endDate = new Date();
        endDate.setTime(endDate.getTime() + 9000);

        //1、创建调度器
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        //2、创建任务job实例，并与PrintWordJob类绑定
        JobDetail jobDetail = JobBuilder.newJob(PrintWordJob.class).usingJobData("jobDetail", "任务实例")
                                                                   .withIdentity("job1", "group1").build();
        //3、构建Trigger触发器，每隔1秒执行一次
        Trigger trigger = TriggerBuilder.newTrigger()
                                        .usingJobData("triggerDetail", "触发器实例")
                                        .withIdentity("trigger1", "triggerGroup1")
                                        .startNow()
                                        .startAt(startDate) //startDate时间节点触发器trigger开始触发任务job执行
                                        .endAt(endDate)  //endDate时间节点触发器失效，不触发任务执行
                                        //触发任务，每1s执行一次
                                        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
                                        //cron表达式设定执行周期
                                        //.withSchedule(CronScheduleBuilder.cronSchedule("* 30 10 ? * 1/5 2018"))
                                        .build();

        //4、执行
        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("---------scheduler start----------");
        scheduler.start();

        //睡眠
        TimeUnit.MINUTES.sleep(1);
        scheduler.shutdown();
        System.out.println("--------scheduler shutdown!-------");
    }
}
