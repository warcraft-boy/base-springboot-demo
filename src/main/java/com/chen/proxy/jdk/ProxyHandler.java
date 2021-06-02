package com.chen.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description: jdk动态代理
 * @Author chenjianwen
 * @Date 2021/5/6
 **/
public class ProxyHandler<T> implements InvocationHandler {

    private T t;

    public ProxyHandler(T t){
        this.t = t;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println();
        Object obj = method.invoke(t, args);
        System.out.println();
        return obj;
    }
}
