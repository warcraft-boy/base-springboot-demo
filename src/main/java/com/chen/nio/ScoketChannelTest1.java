package com.chen.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @ClassName ScoketChannelTest1
 * @description:
 * @author: chenjianwen
 * @create: 2023/05/25
 **/
public class ScoketChannelTest1 {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("www.baidu.com", 80));
        //设置非阻塞模式
        socketChannel.configureBlocking(false);
        //读写操作
        ByteBuffer buffer = ByteBuffer.allocate(16);
        socketChannel.read(buffer);
        socketChannel.close();
        System.out.println("read over");
    }
}
