package com.chen.test.springTest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description:
 * @Author chenjianwen
 * @Date 2021/3/18
 **/
public class TestMain1 {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        Cup cup = ac.getBean("cup", Cup.class);
        cup.weight();
    }
}
