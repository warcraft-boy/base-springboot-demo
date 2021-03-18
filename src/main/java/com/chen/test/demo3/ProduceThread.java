package com.chen.test.demo3;

import lombok.SneakyThrows;

/**
 * @Description:生产者线程
 * @Author chenjianwen
 * @Date 2021/3/15
 **/
public class ProduceThread extends Thread{

    private Product p;

    public ProduceThread(Product p){
        this.p = p;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true){
            p.produce();
        }
    }
}
