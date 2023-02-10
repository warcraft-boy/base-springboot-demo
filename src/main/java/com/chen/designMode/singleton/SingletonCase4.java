package com.chen.designMode.singleton;

/**
 * @author chenjianwen
 * @title: SingletonCase4
 * @description: TODO
 * @date 2022/8/1214:57
 */
public enum SingletonCase4 {

    INSTANCE;

    public void m(){}

    public static void main(String[] args) {
        for(int i=0; i<100; i++){
            new Thread(() -> {
                System.out.println(SingletonCase4.INSTANCE.hashCode());
            }).start();
        }
    }
}
