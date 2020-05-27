package com.chen.test.demo2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Description:
 * @Author chenjianwen
 * @Date 2020/5/22
 **/
public class CircleTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ServiceConfig.class);
        ac.getBean(UserService.class);
    }
}
