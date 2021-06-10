package com.chen.threadPool;

import java.util.concurrent.*;

/**
 * @Description:
 * @Author chenjianwen
 * @Date 2021/6/9
 **/
public class Test {

    private static ExecutorService tpe;
    @org.junit.Test
    public void test(){
        tpe = new ThreadPoolExecutor(2, 5, 20, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(3), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
        for(int i = 0; i < 10; i++){
            tpe.execute(new ThreadTask());
        }
    }

    class ThreadTask implements Runnable{

        @Override
        public void run() {
            try {
//                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + "正在执行");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
