package com.chen.test;

import org.openjdk.jol.info.ClassLayout;

/**
 * @Description: 查看Object对象在内存中的布局
 * @Author chenjianwen
 * @Date 2021/6/17
 **/
public class JolTest {
    public static void main(String[] args) {
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}
