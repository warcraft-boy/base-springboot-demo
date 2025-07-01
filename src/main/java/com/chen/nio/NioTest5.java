package com.chen.nio;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @ClassName 通道传输
 * @description:
 * @author: chenjianwen
 * @create: 2023/05/12
 **/
public class NioTest5 {

    public static void main(String[] args) throws Exception {
        FileChannel fromChannel = new RandomAccessFile("/Users/chenjianwen/myDisk/test.txt", "rw").getChannel();
        FileChannel toChannel = new RandomAccessFile("/Users/chenjianwen/myDisk/ttt.txt", "rw").getChannel();
        long position = 0;
        long size = fromChannel.size();
        toChannel.transferFrom(fromChannel, position, size);
        //或者这种写法
        //fromChannel.transferTo(position, size, toChannel);
        fromChannel.close();
        toChannel.close();
        System.out.println("over");
    }
}
