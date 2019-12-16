package com.chen.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @Description:利用redisTemplate实现redis分布式锁测试用例
 * @Author chenjianwen
 * @Date 2019-12-16
 **/
public class RedisLockDemo {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 多线程情况下出现超卖现象
     * @return
     */
    public String demo1(){
        int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
        if(stock > 0){
            int leftStock = stock - 1;
            stringRedisTemplate.opsForValue().set("stock", leftStock + "");
        }else{
            System.out.println("扣减失败，库存不足");
        }
        return "end";
    }

    /**
     *多服务器节点出现超卖现象
     * @return
     */
    public String demo2(){
        synchronized (this){
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if(stock > 0){
                int leftStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", leftStock + "");
            }else{
                System.out.println("扣减失败，库存不足");
            }
        }
        return "end";
    }
}
