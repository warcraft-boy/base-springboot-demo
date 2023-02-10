package com.chen.test.demo7;

/**
 * @ClassName SonClass
 * @description:
 * @author: chenjianwen
 * @create: 2022/12/09
 **/
public class SonClass extends FatherClass{

    static {
        System.out.println("子类静态代码块");
    }

    {
        System.out.println("子类非静态代码块");
    }

    public SonClass(){
        System.out.println("子类构造函数");
    }
}
