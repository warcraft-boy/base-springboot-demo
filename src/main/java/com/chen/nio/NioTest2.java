package com.chen.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName 拷贝
 * @description:
 * @author: chenjianwen
 * @create: 2023/05/12
 **/
public class NioTest2 {
    public static void main(String[] args) throws Exception {
        FileChannel inChannel = new RandomAccessFile("/Users/chenjianwen/myDisk/diao.jpg", "rw").getChannel();
        FileChannel outChannel = new RandomAccessFile("/Users/chenjianwen/myDisk/diaosi.jpg", "rw").getChannel();
        ByteBuffer buff = ByteBuffer.allocate(1024);
        while(inChannel.read(buff) != -1){
            buff.flip();
            outChannel.write(buff);
            buff.clear();
        }
        inChannel.close();
        outChannel.close();
    }
}
