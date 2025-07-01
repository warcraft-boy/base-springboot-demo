package com.chen.test;

/**
 * @ClassName StackOverFlowMain
 * @description:
 * @author: chenjianwen
 * @create: 2024/12/10
 **/
public class StackOverFlowMain {

    public static void main(String[] args) {
        flow();
    }

    public static void flow(){
        System.out.println("栈溢出");
        flow();
    }
}
