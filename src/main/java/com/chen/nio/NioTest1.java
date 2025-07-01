package com.chen.nio;

import java.nio.IntBuffer;
import java.util.Random;

/**
 * @ClassName NioTest1
 * @description:
 * @author: chenjianwen
 * @create: 2023/05/12
 **/
public class NioTest1 {

    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(11);
        for(int i = 0; i < 2; i++){
            int random = new Random().nextInt();
            buffer.put(random);
        }
        buffer.flip();
        System.out.println("position>>" + buffer.position() + ";limit>>" + buffer.limit() + ";capacity>>" + buffer.capacity());
        while(buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
        System.out.println("position>>" + buffer.position() + ";limit>>" + buffer.limit() + ";capacity>>" + buffer.capacity());
    }
}
