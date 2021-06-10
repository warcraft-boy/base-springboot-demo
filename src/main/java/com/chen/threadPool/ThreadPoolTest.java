package com.chen.threadPool;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * @Description:
 * @Author chenjianwen
 * @Date 2021/6/8
 **/
public class ThreadPoolTest {
    /**
     * new ThreadPoolExecutor(int corePoolSize,
     *                        int maximumPoolSize,
     *                        long keepAliveTime,
     *                        TimeUnit unit,
     *                        BlockingQueue<Runnable> workQueue,
     *                        ThreadFactory threadFactory,
     *                        RejectedExecutionHandler handler)
     *
     * corePoolSize —— 核心线程数
     * maximumPoolSize —— 最大线程数
     * keepAliveTime —— 超出corePoolSize数目的线程的闲置时候的存活时间，之后会被销毁
     * unit —— 时间单位
     * workQueue —— 任务队列，有new SynchronousQueue<>()队列，有new ArrayBlockingQueue<>()队列，有new LinkedBlockingQueue<>()队列，有new PriorityBlockingQueue<>()队列
     * threadFactory —— 线程工厂，有Executors.defaultThreadFactory()默认工厂，有有Executors.privilegedThreadFactory()优先级工厂等
     * handler —— 拒绝策略，有new ThreadPoolExecutor.AbortPolicy()策略，有new ThreadPoolExecutor.CallerRunsPolicy()策略，有new ThreadPoolExecutor.DiscardOledestPolicy()策略，有new ThreadPoolExecutor.DiscardPolicy()策略
     */
    private static ExecutorService tpe;
    @Test
    public void test() throws Exception {
        tpe = new ThreadPoolExecutor(5, 10, 20, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        Future<U> fu = tpe.submit(new T());
        U u = fu.get();
        System.out.println(u);
    }

    class T implements Callable<U>{

        @Override
        public U call() throws Exception {
            U u = new U();
            u.setName("chen");
            return u;
        }
    }

    class U{
        String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
}
