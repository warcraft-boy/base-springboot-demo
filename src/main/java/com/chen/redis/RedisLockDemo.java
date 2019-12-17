package com.chen.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @Description:利用redisTemplate实现redis分布式锁测试用例
 * @Author chenjianwen
 * @Date 2019-12-16
 **/
public class RedisLockDemo {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 出现问题：多线程情况下出现超卖现象
     * 解决方法：同步块synchronized(){}
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
     * 出现问题：多服务器节点出现超卖现象
     * 解决方法：使用redis分布式锁最基础版本
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

    /**
     * 出现问题：如果业务代码抛出异常，那么就无法释放锁，就会出现 “死锁”
     * 解决方法：使用finally，无论业务代码出现什么异常，都会执行finally代码块
     * @return
     */
    public String demo3(){
        String lockKey = "product_001";
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, "stock");
        if(!result){
            return "error";
        }
        int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
        if(stock > 0){
            int leftStock = stock - 1;
            stringRedisTemplate.opsForValue().set("stock", leftStock + "");
        }else{
            System.out.println("扣减失败，库存不足");
        }
        //释放锁
        stringRedisTemplate.delete(lockKey);
        return "end";
    }

    /**
     * 出现问题：如果释放锁之前整个程序挂了，那么这个锁就没法释放，也会出现 “死锁”
     * 解决方法：设置redis锁失效时间
     * @return
     */
    public String demo4(){
        String lockKey = "product_001";
        try {
            Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, "stock");
            if(!result){
                return "error";
            }
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if(stock > 0){
                int leftStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", leftStock + "");
            }else{
                System.out.println("扣减失败，库存不足");
            }
        } finally {
            //释放锁
            stringRedisTemplate.delete(lockKey);
        }
        return "end";
    }

    /**
     * 出现问题：设置redis值和失效时间之间系统挂了，也会出现死锁
     * 解决方法：将设置值和失效时间放在一块
     * @return
     */
    public String demo5(){
        String lockKey = "product_001";
        try {
            Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, "stock");
            stringRedisTemplate.expire(lockKey, 10, TimeUnit.SECONDS); //设置失效时间
            if(!result){
                return "error";
            }
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if(stock > 0){
                int leftStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", leftStock + "");
            }else{
                System.out.println("扣减失败，库存不足");
            }
        } finally {
            //释放锁
            stringRedisTemplate.delete(lockKey);
        }
        return "end";
    }

    /**
     * 出现问题：失效时间不知道具体值，如果线程的执行时间大于失效时间，那么释放锁之前该锁就会失效，下一个线程就会进来，这样两个或多个线程同时存在，回归到第一个问题，出现“超卖”
     * 解决方法：
     * @return
     */
    public String demo6(){
        String lockKey = "product_001";
        try {
            Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, "stock", 10, TimeUnit.SECONDS);
            if(!result){
                return "error";
            }
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if(stock > 0){
                int leftStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", leftStock + "");
            }else{
                System.out.println("扣减失败，库存不足");
            }
        } finally {
            //释放锁
            stringRedisTemplate.delete(lockKey);
        }
        return "end";
    }
}
