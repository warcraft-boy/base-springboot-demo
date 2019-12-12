package com.chen.redis;

import redis.clients.jedis.Jedis;

/**
 * @Description: <br>
 * @Date: Created in 2019/12/9 <br>
 * @Author: chenjianwen
 */
public class RedisDistributeLock {

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "OK";

    /**
     * 获取分布式锁
     * @param jedis jedis
     * @param lockKey   锁
     * @param requestId    请求标识
     * @param expireTime   过期时间
     * @return
     */
    public static boolean getDistributeLock(Jedis jedis, String lockKey, String requestId, int expireTime){

//        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME,expireTime);
//        jedis.setnx();
        return false;
    }
}
