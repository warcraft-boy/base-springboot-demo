package com.chen.proxy.jdk;

/**
 * @Description:
 * @Author chenjianwen
 * @Date 2021/5/6
 **/
public class Maotai implements Wine {

    @Override
    public void wine() {
        System.out.println("我卖茅台酒");
    }
}
