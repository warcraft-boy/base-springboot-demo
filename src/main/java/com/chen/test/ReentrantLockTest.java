package com.chen.test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName ReentrantLockTest
 * @description:
 * @author: chenjianwen
 * @create: 2023/03/20
 **/
public class ReentrantLockTest {


    private static ReentrantLockTest reentrantLockTest;
    private static ReentrantLock reentrantLock = new ReentrantLock();

    public static ReentrantLockTest getInstance(){
        reentrantLock.lock();
        try {
            if(reentrantLockTest == null){
                Thread.sleep(300);
                reentrantLockTest = new ReentrantLockTest();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
        return reentrantLockTest;
    }

    public static void main(String[] args) {
        for(int i = 0; i<300; i++){
            new Thread(() -> {
                System.err.println(ReentrantLockTest.getInstance().hashCode());
            }).start();
        }
    }
}
