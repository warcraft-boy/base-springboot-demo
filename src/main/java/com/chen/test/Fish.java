package com.chen.test;

import lombok.Data;

/**
 * @Description:
 * @Author chenjianwen
 * @Date 2020/5/20
 **/
@Data
public class Fish {

    public void swim(String name){
        System.out.println(name + " swim");
    }
}
