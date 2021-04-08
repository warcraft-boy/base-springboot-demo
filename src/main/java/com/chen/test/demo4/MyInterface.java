package com.chen.test.demo4;

/**
 * @Description:
 * @Author chenjianwen
 * @Date 2021/3/31
 **/
public interface MyInterface {

    String face();
}

class MyClass{
    void c(String n, MyInterface mi){
        System.out.println(n + mi.face());
    }
}

class Main{
    public static void main(String[] args) {
        new MyClass().c("my", () -> {return "love";});
    }
}