package com.chen.nio;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

/**
 * @ClassName DatagramChannelTest1
 * @description:
 * @author: chenjianwen
 * @create: 2023/05/31
 **/
public class DatagramChannelTest1 {

    @Test
    public void sendDatagram() throws Exception {
        DatagramChannel sendChannel = DatagramChannel.open();
        InetSocketAddress sendAddress = new InetSocketAddress("127.0.0.1", 9999);
        while(true){
            ByteBuffer buffer = ByteBuffer.wrap("发送message".getBytes("UTF-8"));
            sendChannel.send(buffer, sendAddress);
            System.out.println("发送完成");
            Thread.sleep(1000);
        }
    }

    @Test
    public void receiveDatagram() throws Exception {
        DatagramChannel receiveChannel = DatagramChannel.open();
        InetSocketAddress receiveAddress = new InetSocketAddress(9999);
        receiveChannel.bind(receiveAddress);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while(true){
            buffer.clear();
            SocketAddress socketAddress = receiveChannel.receive(buffer);
            buffer.flip();
            System.err.println(socketAddress.toString());
            System.err.println(Charset.forName("UTF-8").decode(buffer));
        }
    }
}
