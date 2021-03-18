package com.chen.test.demo3;

/**
 * @Description:
 * @Author chenjianwen
 * @Date 2021/3/15
 **/
public class Product {

    private static volatile int p = 1; //假设这里只有一个并保持一个有效商品

    /**
     * 生产商品
     * @throws InterruptedException
     */
    public synchronized void produce() throws InterruptedException {
        if(p == 1){
            wait();
        }
        p += 1;
        System.out.println("produce = " + p);
        notify();
    }

    /**
     * 消费商品
     * @throws InterruptedException
     */
    public synchronized void consume() throws InterruptedException {
        if(p == 0){
            wait();
        }
        p -= 1;
        System.err.println("consume = " + p);
        notify();
    }
}
