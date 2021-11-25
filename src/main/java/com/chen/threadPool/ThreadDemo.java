package com.chen.threadPool;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @Description:
 * @Author chenjianwen
 * @Date 2021/6/9
 **/
public class ThreadDemo {

    private static ExecutorService tpe;
    @Test
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


    @Test
    public void threadTest1() throws InterruptedException {
        Long start = System.currentTimeMillis();
        Random random = new Random();
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < 100000; i++){
            Thread t = new Thread(() -> list.add(random.nextInt()));
            t.start();
            t.join();
        }
        System.out.println("时间 = " + (System.currentTimeMillis() - start));
        System.out.println("大小 = " + list.size());
    }

    @Test
    public void threadTest2() throws InterruptedException {
        Long start = System.currentTimeMillis();
        Random random = new Random();
        List<Integer> list = new ArrayList<>();
        ExecutorService es = Executors.newSingleThreadExecutor();
        for(int i = 0; i < 100000; i++){
            es.execute(() -> list.add(random.nextInt()));
        }
        es.shutdown();
        System.out.println("时间 = " + (System.currentTimeMillis() - start));
        System.out.println("大小 = " + list.size());
    }

}
