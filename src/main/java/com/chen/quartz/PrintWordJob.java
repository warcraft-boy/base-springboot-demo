package com.chen.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: <br>任务
 * @Date: Created in 2020/1/3 <br>
 * @Author: chenjianwen
 */
public class PrintWordJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String printTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println(jobExecutionContext.getJobDetail().getJobDataMap().get("jobDetail"));
        System.out.println(jobExecutionContext.getTrigger().getJobDataMap().get("triggerDetail"));
        System.out.println("PrintWordJob start at" + printTime + ",prints:hello world!");
    }
}
