package com.chen.test.demo5;

/**
 * @Description:
 * @Author chenjianwen
 * @Date 2021/5/10
 **/
public class Son extends Father {
    static int a = 11;
    public Son(){
        super();
        System.out.println("I am son");
    }

    public static void main(String[] args) {
        Son son = new Son();
        System.out.println(a);
    }
}
