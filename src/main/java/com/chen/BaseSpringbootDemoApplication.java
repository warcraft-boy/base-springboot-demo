package com.chen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableRabbit  //开启基于注解的rabbitmq模式，@EnableRabbit配合@RabbitListener注解可监听消息队列的内容
@SpringBootApplication
//@MapperScan(basePackages = "com.chen.mysql") //如果是多数据源，这里就不用配置@MapperScan注解，而需要在各自的配置文件中配置
//@ComponentScan(basePackages = "com.chen")
@EnableTransactionManagement
public class BaseSpringbootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseSpringbootDemoApplication.class, args);
    }

}
