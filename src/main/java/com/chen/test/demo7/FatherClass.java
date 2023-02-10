package com.chen.test.demo7;

/**
 * @ClassName FatherClass
 * @description:
 * @author: chenjianwen
 * @create: 2022/12/09
 **/
public class FatherClass {

    static {
        System.out.println("父类静态代码块");
    }

    {
        System.out.println("父类非静态代码块");
    }

    public FatherClass(){
        System.out.println("父类构造函数");
    }
}
