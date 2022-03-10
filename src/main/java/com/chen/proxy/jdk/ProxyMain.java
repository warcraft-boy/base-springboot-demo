package com.chen.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @Description: jdk动态代理
 * @Author chenjianwen
 * @Date 2021/5/6
 **/
public class ProxyMain {
    public static void main(String[] args) {
        Wine wine = new Maotai();
        InvocationHandler ih = new ProxyHandler<Wine>(wine);
        Wine wineInstance = (Wine) Proxy.newProxyInstance(Wine.class.getClassLoader(), wine.getClass().getInterfaces(), ih);
        wineInstance.wine();
    }
}
