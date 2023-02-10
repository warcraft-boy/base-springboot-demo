package com.chen.test.demo6;

/**
 * @ClassName TestFormula
 * @description:
 * @author: chenjianwen
 * @create: 2022/05/07
 **/
public class TestFormula {
    public static void main(String[] args) {
        Formula formula = new Formula() {
            @Override
            public double calculate(int a) {
                return sqrt(a*100);
            }

            @Override
            public double sqrt(int a) {
                return 12;
            }
        };
        System.out.println(formula.sqrt(3));
//        System.out.println(formula.calculate(2));
    }

}
