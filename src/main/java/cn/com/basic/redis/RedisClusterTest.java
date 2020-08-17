package cn.com.basic.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.SafeEncoder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ZhaiJiaYi on 2016/7/13.
 */


public class RedisClusterTest {


    public static void main(String[] args) {

        JedisPool pool = new JedisPool("127.0.0.1", 6479);
        pool.getResource().set(SafeEncoder.encode("myredis_demo_test.ctest_0"), SafeEncoder.encode(String.valueOf(9)));

      /*  Object object = pool.getResource().eval("local current = redis.call('incrBy',KEYS[1],ARGV[1]);" +
                " if current == tonumber(ARGV[1]) then" +
                " local t = redis.call('ttl',KEYS[1]);" +
                " if t == -1 then " +
                " redis.call('expire',KEYS[1],ARGV[2])" +
                " end;" +
                " end;" +
                " return current", 1, new String[]{"myredis_demo_test.ctest_0", String.valueOf(7), String.valueOf(30)});

        System.out.println("target - " + object);*/
       /* try (Jedis jedis = new Jedis("127.0.0.1", 6379)) {
            jedis.set("test", "haha");
          *//*  for (int i = 2; i < 1000; i++) {
                String key = "a" + i;
                jedis.set(key, "haha");
            }
*//*
           System.out.println("dbsize - "+jedis.dbSize()) ;
        }*/
        JedisCluster jedisCluster = getCluster();
       /* System.out.println("是否存在:"+jedisCluster.exists("hello"));*/
        //  RedisClusterTools.addJedisCluster("testCluster", jedisCluster);
        //  System.out.println("dbsize - "+RedisClusterTools.clusterDbSize("testCluster"));
       /* System.out.println("新增<'username','wukong'>的键值对："+jedisCluster.set("username", "aaaa"));
        System.out.println("是否存在:"+jedisCluster.exists("username"));*/
    }

    private static JedisCluster getCluster() {
        // 添加集群的服务节点Set集合
        Set<HostAndPort> hostAndPortSet = new HashSet<HostAndPort>();
        // 添加节点
        hostAndPortSet.add(new HostAndPort("127.0.0.1", 6379));
        hostAndPortSet.add(new HostAndPort("127.0.0.1", 6380));
        hostAndPortSet.add(new HostAndPort("127.0.0.1", 6381));
        hostAndPortSet.add(new HostAndPort("127.0.0.1", 6382));
        hostAndPortSet.add(new HostAndPort("127.0.0.1", 6383));
        hostAndPortSet.add(new HostAndPort("127.0.0.1", 6384));


        // Jedis连接池配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大空闲连接数, 默认8个
        jedisPoolConfig.setMaxIdle(100);
        // 最大连接数, 默认8个
        jedisPoolConfig.setMaxTotal(500);
        //最小空闲连接数, 默认0
        jedisPoolConfig.setMinIdle(0);
        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        jedisPoolConfig.setMaxWaitMillis(2000); // 设置2秒
        //对拿到的connection进行validateObject校验
        jedisPoolConfig.setTestOnBorrow(true);
        return new JedisCluster(hostAndPortSet, jedisPoolConfig);
    }

}