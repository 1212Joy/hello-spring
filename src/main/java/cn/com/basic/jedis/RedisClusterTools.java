package cn.com.basic.jedis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhaijiayi on 2019/5/6.
 */
@Slf4j
public class RedisClusterTools {
    private static String defaultName = "test";
    private static final ConcurrentHashMap<String, JedisCluster> jedisClusterMap = new ConcurrentHashMap<>();

    public static void addJedisCluster(String jedisClusterName,
                                       JedisCluster jedisCluster) {
        if (jedisCluster != null && jedisClusterName != null && !jedisClusterMap.containsKey(jedisClusterName)) {
            jedisClusterMap.put(jedisClusterName, jedisCluster);
        }
    }

    /**
     * 获得一个JedisCluster
     *
     * @param jedisClusterName
     * @return
     */
    public static JedisCluster getJedisCluster(String jedisClusterName) {
        return jedisClusterMap.get(jedisClusterName);
    }

    /**
     * 执行集群指令
     *
     * @param jedisClusterName
     * @param action
     * @param <T>
     * @return
     */
    public static <T> T clusterExecute(String jedisClusterName,
                                       JedisClusterCallback<T> action) {
        JedisCluster jedis = getJedisCluster(jedisClusterName);
        try {
            return action.doInJedisCluster(jedis);
        } catch (Throwable throwable) {
            log.error("clusterExecute|action = {} , exception = {}", action, throwable);
            throw new RuntimeException(throwable);
        }
    }

    /**
     * 执行集群指令
     *
     * @param action
     * @param <T>
     * @return
     */
    public static <T> T clusterExecute(JedisClusterCallback<T> action) {
        JedisCluster jedis = getJedisCluster(defaultName);
        try {
            return action.doInJedisCluster(jedis);
        } catch (Throwable throwable) {
            log.error("clusterExecute|action = {} , exception = {}", action, throwable);
            throw new RuntimeException(throwable);
        }
    }

    /**
     * 由于JedisCluster没有实现keys操作，这里自己实现以下
     *
     * @param pattern
     * @return
     */
    public static Set<String> clusterKeys(String jedisClusterName,
                                          String pattern) {
        Set<String> keys = new HashSet<>();
        Map<String, JedisPool> clusterNodes = getJedisCluster(jedisClusterName).getClusterNodes();
        return clusterKeys(pattern, keys, clusterNodes);
    }

    private static Set<String> clusterKeys(String pattern,
                                           Set<String> keys,
                                           Map<String, JedisPool> clusterNodes) {
        for (String k : clusterNodes.keySet()) {
            JedisPool jp = clusterNodes.get(k);
            Jedis connection = jp.getResource();
            try {
                keys.addAll(connection.keys(pattern));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.close();
            }
        }
        return keys;
    }

    /**
     * 由于JedisCluster没有实现keys操作，这里自己实现以下
     *
     * @param pattern
     * @return
     */
    public static Set<String> clusterKeys(String pattern) {
        Set<String> keys = new HashSet<>();
        Map<String, JedisPool> clusterNodes = getJedisCluster(defaultName).getClusterNodes();
        return clusterKeys(pattern, keys, clusterNodes);
    }

    public static Set<byte[]> clusterKeys(String jedisClusterName,
                                          byte[] pattern) {
        Set<byte[]> keys = new HashSet<>();
        Map<String, JedisPool> clusterNodes = getJedisCluster(jedisClusterName).getClusterNodes();
        return clusterKeys(pattern, keys, clusterNodes);
    }

    private static Set<byte[]> clusterKeys(byte[] pattern,
                                           Set<byte[]> keys,
                                           Map<String, JedisPool> clusterNodes) {
        for (Object k : clusterNodes.keySet()) {
            JedisPool jp = clusterNodes.get(k);
            Jedis connection = jp.getResource();
            try {
                keys.addAll(connection.keys(pattern));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.close();
            }
        }
        return keys;
    }

    public static Set<byte[]> clusterKeys(byte[] pattern) {
        Set<byte[]> keys = new HashSet<>();
        Map<String, JedisPool> clusterNodes = getJedisCluster(defaultName).getClusterNodes();
        return clusterKeys(pattern, keys, clusterNodes);
    }

    public static void clusterFlushDB(String jedisClusterName) {
        Map<String, JedisPool> clusterNodes = getJedisCluster(jedisClusterName).getClusterNodes();
        clusterFlushDb1(clusterNodes);
    }

    private static void clusterFlushDb1(Map<String, JedisPool> clusterNodes) {
        for (Object k : clusterNodes.keySet()) {
            JedisPool jp = clusterNodes.get(k);
            Jedis connection = jp.getResource();
            try {
                connection.flushDB();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.close();
            }
        }
    }

    public static Long clusterDbSize(String jedisClusterName) {
        Long total = 0L;
        Map<String, JedisPool> clusterNodes = getJedisCluster(jedisClusterName).getClusterNodes();
        return clusterDbSize(total, clusterNodes);
    }

    private static Long clusterDbSize(Long total,
                                      Map<String, JedisPool> clusterNodes) {
        for (Object k : clusterNodes.keySet()) {
            JedisPool jp = clusterNodes.get(k);
            Jedis connection = jp.getResource();
            try {
                total += connection.dbSize();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.close();
            }
        }
        return total;
    }

    public static Long clusterDbSize() {
        Long total = 0L;
        Map<String, JedisPool> clusterNodes = getJedisCluster(defaultName).getClusterNodes();
        return clusterDbSize(total, clusterNodes);
    }
}