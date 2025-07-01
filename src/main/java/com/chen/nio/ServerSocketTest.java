package com.chen.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @ClassName ServerSocketTest
 * @description:
 * @author: chenjianwen
 * @create: 2023/05/16
 **/
public class ServerSocketTest {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        ByteBuffer buffer = ByteBuffer.wrap("hello world".getBytes());
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //绑定
        ssc.socket().bind(new InetSocketAddress(port));
        //设置非阻塞模式
        ssc.configureBlocking(false);
        //监听是否有新的链接传入
        while(true){
            System.out.println("等待链接。。。");
            SocketChannel accept = ssc.accept();
            if(accept == null){
                System.out.println("没有新的链接传入");
                Thread.sleep(1000);
            }else {
                System.out.println("有新的链接传入" + accept.socket().getRemoteSocketAddress());
                buffer.rewind();
                accept.write(buffer);
                accept.close();
            }
        }

    }
}
