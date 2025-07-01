package com.chen.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName 写
 * @description:
 * @author: chenjianwen
 * @create: 2023/05/12
 **/
public class NioTest4 {

    public static void main(String[] args) throws Exception {
        FileChannel channel = new RandomAccessFile("/Users/chenjianwen/myDisk/test.txt", "rw").getChannel();
        ByteBuffer buff = ByteBuffer.allocate(1024);
        String str = "alkdjfl阿里快点发来看";
        buff.put(str.getBytes());
        buff.flip();
        while(buff.hasRemaining()){
            channel.write(buff);
        }
        channel.close();
    }
}
