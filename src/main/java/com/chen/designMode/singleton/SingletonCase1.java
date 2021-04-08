package com.chen.designMode.singleton;

/**
 * @Description:单例模式-饿汉式
 * 特点：1、无参构造器私有
 *      2、内部构造一个静态的对象
 *      3、唯一一个公共的对外提供实例
 *
 * 缺点：不管用到与否，类加载时就完成实例化（你不用它，加载它干嘛）
 * @Author chenjianwen
 * @Date 2021/4/6
 **/
public class SingletonCase1 {

    private static final SingletonCase1 INSTANCE = new SingletonCase1();

    private SingletonCase1(){}

    public static SingletonCase1 getInstance(){
        return INSTANCE;
    }
}
