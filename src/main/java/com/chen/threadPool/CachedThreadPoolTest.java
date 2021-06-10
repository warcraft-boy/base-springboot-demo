package com.chen.threadPool;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: 缓存线程池
 * @Author chenjianwen
 * @Date 2021/6/8
 **/
public class CachedThreadPoolTest {

    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "正在执行！");
            }
        });

//        es.execute(() -> {System.out.println(Thread.currentThread().getName() + "正在执行！");});
    }
}
