package cn.com.basic.jedis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * Created by zhaijiayi on 2019/3/28.
 */
public class JedisConfig {
    public static final int DEFAULT_CONN_TIMEOUT = 5000;               // 默认连接超时时间

    public static final int DEFAULT_SO_TIMEOUT = 5000;                 // 默认读取超时时间

    public static final int DEFAULT_MAX_WAITMILLIS = 5000;             // 默认获取连接超时时间

    public static final int DEFAULT_MAX_REDIRECTS = 10;                // 最大跳转次数

    public static final int DEFAULT_MAXTOTAL = 30;                     // 默认最大连接数

    public static final int DEFAULT_MINIDLE = 2;                       // 默认最小空闲连接数

    public static final int DEFAULT_MAXIDLE = 30;                      // 默认最大空闲连接数

    public static final boolean TEST_WHILE_IDLE = true;                // 空闲时检查连接有效性

    public static final boolean TEST_ON_BORROW = false;                // 在获取连接的时候检查有效性

    public static final boolean TEST_ON_RETURN = false;                // 在退还连接的时候检查有效性

    public static final boolean BLOCK_WHEN_EXHAUSTED = true;            // 连接耗尽时是否阻塞

    public static final int MIN_EVICTABLE_IDLE_TIME_MS = 5 * 60000;    // 空闲连接多长时间后会被收回

    public static final int TIME_BETWEEN_EVICTION_RUN_MS = 60000;      // 检查连接池中空闲连接时间间隔

    public static final int NUM_TEST_PER_EVICTION_RUN = 1024;          // 每次释放连接检查的最大数目

    public static final int DEFAULT_TRY_REBUILD_JEDIS_CLUSTER_INTERVAL = 5 * 1000;    // 创建连接失败的默认重试时间


    public static GenericObjectPoolConfig getDefaultPoolConfig() {
        GenericObjectPoolConfig conf = new GenericObjectPoolConfig();

        conf.setMaxWaitMillis(DEFAULT_MAX_WAITMILLIS);
        conf.setMaxTotal(DEFAULT_MAXTOTAL);
        conf.setMaxIdle(DEFAULT_MAXIDLE);
        conf.setMinIdle(DEFAULT_MINIDLE);
        conf.setTestWhileIdle(TEST_WHILE_IDLE);
        conf.setTestOnBorrow(TEST_ON_BORROW);
        conf.setTestOnReturn(TEST_ON_RETURN);
        conf.setMinEvictableIdleTimeMillis(MIN_EVICTABLE_IDLE_TIME_MS);
        conf.setTimeBetweenEvictionRunsMillis(TIME_BETWEEN_EVICTION_RUN_MS);
        conf.setNumTestsPerEvictionRun(NUM_TEST_PER_EVICTION_RUN);

        return conf;
    }

}
