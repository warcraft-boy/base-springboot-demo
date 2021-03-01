package com.chen.test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:
 * @Author chenjianwen
 * @Date 2021/3/1
 **/
public class Lb {

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static int getAndIncrement(){
        int current;
        int next;
        do{
            current = atomicInteger.get();
            next = current >= Integer.MAX_VALUE ? 0 :current + 1;
            //atomicInteger.compareAndSet(current, next) 比较的是atomicInteger和current，如果相等，则返回true,且将atomicInteger更新为next
        }while(!atomicInteger.compareAndSet(current, next));
        System.out.println("next = " + next);
        return next;
    }

    public static void main(String[] args) {
        System.out.println(getAndIncrement());
    }
}
