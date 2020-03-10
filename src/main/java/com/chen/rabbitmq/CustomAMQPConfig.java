package com.chen.rabbitmq;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: <br>自定义rabbitmq配置类
 * @Date: Created in 2020/3/9 <br>
 * @Author: chenjianwen
 */
@Configuration
public class CustomAMQPConfig {

    /**
     * 自定义MessageConverter
     * 因为rabbitmq默认的SimpleMessageConverter不支持对象，会报出如下异常
     * java.lang.IllegalArgumentException: SimpleMessageConverter only supports String, byte[] and Serializable payloads, received: com.chen.rabbitmq.Book
     * @return
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
