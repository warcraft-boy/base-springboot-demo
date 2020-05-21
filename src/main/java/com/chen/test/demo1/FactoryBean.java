package com.chen.test.demo1;


/**
 * @Description:
 * @Author chenjianwen
 * @Date 2020/5/21
 **/
public class FactoryBean {

    private FactoryBean(){}
    private static IniFileReader iniFileReader = new IniFileReader();
    public static <T> T getInstance(Class<?> clazz){
        try {
            return (T)clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T getInstance1(String beanName) throws IllegalAccessException, InstantiationException {
        return (T) iniFileReader.getIocMap().get(beanName);
    }
}
