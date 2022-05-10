package com.chen.test.demo6;

/**
 * @ClassName FormulaImpl
 * @description:
 * @author: chenjianwen
 * @create: 2022/05/07
 **/
public class FormulaImpl implements Formula{
    @Override
    public double calculate(int a) {
        return 0;
    }

    @Override
    public double sqrt(int a) {
//        return Formula.super.sqrt(a);
        return a;
    }
}
