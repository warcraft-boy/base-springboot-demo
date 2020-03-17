package com.chen.aop;


/**
 * @Description:作为aop的切点类
 * @Author chenjianwen
 * @Date 2020-03-17
 **/
public class PointCutTest {

    public void cut1(String s1){
        System.out.println(s1);
    }

    public void cut2(String s2){
        System.out.println(s2);
    }
}
