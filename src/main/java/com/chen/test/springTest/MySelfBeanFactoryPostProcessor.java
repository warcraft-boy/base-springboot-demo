package com.chen.test.springTest;

import lombok.val;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @Description:
 * @Author chenjianwen
 * @Date 2021/3/22
 **/
public class MySelfBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//        BeanDefinition cup = beanFactory.getBeanDefinition("cup");
//        cup.setBeanClassName("abc");
        System.out.println("调用自定义的beanFactoryPostProcessor");
    }
}
