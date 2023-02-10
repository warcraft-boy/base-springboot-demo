package com.chen.designMode.singleton;

/**
 * @ClassName SingletonCase3
 * @description:
 * @author: chenjianwen
 * @create: 2022/08/12
 **/
public class SingletonCase3 {

    private SingletonCase3 (){}

    private static class SingletonCase3Builder{
        private final static SingletonCase3 instance = new SingletonCase3();
    }

    public static SingletonCase3 getInstance(){
        return SingletonCase3Builder.instance;
    }

    public static void main(String[] args) {
        for(int i = 0; i<100; i++){
            new Thread(() -> {
                System.out.println(SingletonCase3.getInstance().hashCode());
            }).start();
        }
    }
}
