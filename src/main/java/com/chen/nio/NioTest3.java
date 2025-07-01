package com.chen.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName 读
 * @description:
 * @author: chenjianwen
 * @create: 2023/05/12
 **/
public class NioTest3 {
    public static void main(String[] args) throws Exception {
        FileChannel readChannel = new RandomAccessFile("/Users/chenjianwen/myDisk/ttt.txt", "rw").getChannel();
        ByteBuffer buff = ByteBuffer.allocate(1024);
        while(readChannel.read(buff) != -1){
            buff.flip();
            while(buff.hasRemaining()){
                System.out.println(StandardCharsets.UTF_8.decode(buff));
            }
            buff.clear();
        }
        readChannel.close();
    }
}
