package com.chen.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author chenjianwen
 * @Date 2020/5/20
 **/
@Configuration
public class FishConfig {

    @Bean
    public Fish fish(){
        return new Fish();
    }
}
