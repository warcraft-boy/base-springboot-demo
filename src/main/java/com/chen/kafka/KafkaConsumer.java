package com.chen.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Description: kafka监听器
 * @Author chenjianwen
 * @Date 2021/12/9
 **/
//@Component
//public class KafkaConsumer {
//
//    @KafkaListener(topics = "chenjianwen")
//    public void listener(ConsumerRecord<Integer, String> consumerRecord){
//        System.err.println("消费者收到消息");
//        System.err.println("主题：" + consumerRecord.topic());
//        System.err.println("分区：" + consumerRecord.partition());
//        System.err.println("偏移量：" + consumerRecord.offset());
//        System.err.println("key：" + consumerRecord.key());
//        System.err.println("value：" + consumerRecord.value());
//
//    }
//}
