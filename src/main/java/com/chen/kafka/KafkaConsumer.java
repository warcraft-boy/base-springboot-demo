//package com.chen.kafka;
//
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.annotation.PartitionOffset;
//import org.springframework.kafka.annotation.TopicPartition;
//import org.springframework.kafka.support.Acknowledgment;
//import org.springframework.stereotype.Component;
//
///**
// * @Description: kafka监听器
// * @Author chenjianwen
// * @Date 2021/12/9
// **/
//@Component
//public class KafkaConsumer {
//
//    @KafkaListener(topics = "chenjianwen", groupId = "MyGroup1")
//    public void listener(ConsumerRecord<Integer, String> consumerRecord, Acknowledgment ack){
//        System.err.println("消费者收到消息");
//        System.err.println("主题：" + consumerRecord.topic());
//        System.err.println("分区：" + consumerRecord.partition());
//        System.err.println("偏移量：" + consumerRecord.offset());
//        System.err.println("key：" + consumerRecord.key());
//        System.err.println("value：" + consumerRecord.value());
//        ack.acknowledge(); //手动提交offset，配置文件中auto-offset-reset为false这里才有意义，若为true，这里不需要
//    }
//
//
//    /**
//     * 消费者的详细配置
//     * @param consumerRecord
//     * @param ack
//     */
//    @KafkaListener(groupId = "testGroup", topicPartitions = { //消费者组testGroup的消费者
//            @TopicPartition(topic = "topic1", partitions = {"0", "1"}), //消费topic1主题下0和1号分区
//            @TopicPartition(topic = "topic2", partitions = "0", //消费topic2主题下的0号分区
//                    partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "5")) //消费topic2主题下1号分区，且从偏移量5开始消费
//    }, concurrency = "3") //concurrency指同组下的消费者个数，即为并发消费数，建议小于等于分区数
//    public void listener2(ConsumerRecord<Integer, String> consumerRecord, Acknowledgment ack){
//        System.err.println("消费者收到消息");
//        System.err.println("主题：" + consumerRecord.topic());
//        System.err.println("分区：" + consumerRecord.partition());
//        System.err.println("偏移量：" + consumerRecord.offset());
//        System.err.println("key：" + consumerRecord.key());
//        System.err.println("value：" + consumerRecord.value());
//        ack.acknowledge(); //手动提交offset，配置文件中auto-offset-reset为false这里才有意义，若为true，这里不需要
//    }
//}
