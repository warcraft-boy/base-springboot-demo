package com.chen.test.demo1;

import org.junit.Test;

/**
 * @Description:
 * @Author chenjianwen
 * @Date 2020/5/21
 **/
public class TestMsg {

    @Test
    public void test01(){
        IMessageService iMessageService = FactoryBean.getInstance(IMessageServiceImpl.class);
        System.out.println(iMessageService.getMsg("shuffle"));
    }

    @Test
    public void test02(){
        IniFileReader iniFileReader = new IniFileReader();
        iniFileReader.readIni();
        System.out.println(iniFileReader.getIocMap());
    }

    @Test
    public void test03() throws InstantiationException, IllegalAccessException {
        IMessageService iMessageService = FactoryBean.getInstance1("message");
        System.out.println(iMessageService.getMsg("hello world"));
    }
}
