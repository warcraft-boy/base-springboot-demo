package com.chen.test.demo1;

/**
 * @Description:
 * @Author chenjianwen
 * @Date 2020/5/22
 **/
public class CircleDep {
    public static void main(String[] args) {
        A a = new A();
        B b = new B();
        a.setB(b);
        b.setA(a);
    }
}

class A{
    public B b;
    public void setB(B b){
        this.b = b;
    }
}

class B{
    public A a;
    public void setA(A a){
        this.a = a;
    }
}