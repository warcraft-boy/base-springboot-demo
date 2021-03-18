package com.chen.test.demo3;


/**
 * @Description:
 * @Author chenjianwen
 * @Date 2021/3/15
 **/
public class ThreadTest {

    public static void main(String[] args) {
        Product p = new Product();
        new ProduceThread(p).start();
        new ConsumerThread(p).start();
    }
}
