package com.chen.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: 单线程的线程池
 * @Author chenjianwen
 * @Date 2021/6/8
 **/
public class SingleThreadPoolTest {
    public static void main(String[] args) {
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(() -> System.out.println(Thread.currentThread().getName() + "正在执行！"));
    }
}
