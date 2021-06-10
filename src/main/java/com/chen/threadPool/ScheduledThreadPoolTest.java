package com.chen.threadPool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 周期性任务执行的线程池
 * @Author chenjianwen
 * @Date 2021/6/8
 **/
public class ScheduledThreadPoolTest {
    public static void main(String[] args) {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(5);
        ses.scheduleAtFixedRate(() -> {
            System.out.println("延迟5秒后，每1秒执行一次");
        }, 5, 1, TimeUnit.SECONDS);
    }
}
