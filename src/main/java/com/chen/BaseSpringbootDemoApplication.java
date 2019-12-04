package com.chen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = "com.chen.mysql.dao")
//@ComponentScan(basePackages = "com.chen")
@EnableTransactionManagement
public class BaseSpringbootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseSpringbootDemoApplication.class, args);
    }

}
