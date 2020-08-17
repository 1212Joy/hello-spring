package cn.com.basic.jedis;

import redis.clients.jedis.JedisCluster;

/**
 * Created by zhaijiayi on 2019/5/6.
 */
public interface JedisClusterCallback<T> {
    T doInJedisCluster(JedisCluster jedis) throws Throwable;
}
