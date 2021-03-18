package com.chen.test;

/**
 * @Description:
 * @Author chenjianwen
 * @Date 2021/3/15
 **/
public class VolitileDemo {

    private static boolean flag = true;

    public static void main(String[] args) {
        new Thread(() -> {
            while(true){
                if(flag){
                    System.out.println("turn on");
                    flag = false;
                }
            }
        }).start();

        new Thread(() -> {
            while(true){
                if(!flag){
                    System.err.println("turn off");
                    flag = true;
                }
            }
        }).start();
    }
}
