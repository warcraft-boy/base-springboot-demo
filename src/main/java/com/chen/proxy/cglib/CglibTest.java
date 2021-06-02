package com.chen.proxy.cglib;

/**
 * @Description:
 * @Author chenjianwen
 * @Date 2021/6/2
 **/
public class CglibTest {
    public static void main(String[] args) {
        CglibProxy cglibProxy = new CglibProxy();
        CglibCase cglibCase = (CglibCase) cglibProxy.getProxy(CglibCase.class);
        cglibCase.test();
    }
}
