package com.chen.test.demo6;

/**
 * @author chenjianwen
 * @title: Formula
 * @description: TODO
 * @date 2022/5/716:31
 */
public interface Formula {

    double calculate(int a);

    default double sqrt(int a) {
        return Math.sqrt(a);
    }
}
