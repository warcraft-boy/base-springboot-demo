package com.chen.designMode.singleton;

/**
 * @Description:单例模式-懒汉式
 *
 * @Author chenjianwen
 * @Date 2021/4/6
 **/
public class SingletonCase2 {

    private static SingletonCase2 INSTANCE;

    private SingletonCase2(){}

    //线程不安全
//    public static SingletonCase2 getInstance(){
//        if(INSTANCE == null){
//            INSTANCE = new SingletonCase2();
//        }
//        return INSTANCE;
//    }

    //线程安全
    public static SingletonCase2 getInstance(){

        if(INSTANCE == null){       //避免锁竞争，影响效率
            synchronized (SingletonCase2.class){
                if(INSTANCE == null){
                    INSTANCE = new SingletonCase2();
                }
            }
        }
        return INSTANCE;
    }

    //多线程验证线程安全性，同一个对象的hash码应该是相同的
    public static void main(String[] args) {
        for(int i = 0; i < 200; i++){
            new Thread(() -> System.out.println(SingletonCase2.getInstance().hashCode())).start();
        }
    }
}
