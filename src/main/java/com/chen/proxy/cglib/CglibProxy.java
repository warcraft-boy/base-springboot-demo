package com.chen.proxy.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Description: cglib动态代理
 * @Author chenjianwen
 * @Date 2021/6/2
 **/
public class CglibProxy implements MethodInterceptor {

    private final Enhancer enhancer = new Enhancer();

    public Object getProxy(Class clazz){
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("前置代理");
        Object obj = methodProxy.invokeSuper(o, objects);
        System.out.println("后置代理");
        return obj;
    }
}
