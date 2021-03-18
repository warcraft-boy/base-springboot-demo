package com.chen.test.demo3;

import lombok.SneakyThrows;

/**
 * @Description:消费者线程
 * @Author chenjianwen
 * @Date 2021/3/15
 **/
public class ConsumerThread extends Thread {

    private Product p;

    public ConsumerThread(Product p){
        this.p = p;
    }

    @SneakyThrows
    @Override
    public void run() {
        while(true){
            p.consume();
        }
    }
}
