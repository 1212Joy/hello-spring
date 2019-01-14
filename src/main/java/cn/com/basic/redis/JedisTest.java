package cn.com.basic.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by ZhaiJiaYi on 2016/7/13.
 */


public class JedisTest {


    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        for (int i=2;i<1000;i++){
            String key ="a"+i;
            jedis.set(key,"haha");
        }

        System.out.println("dbSize: "+jedis.dbSize());
    }

}