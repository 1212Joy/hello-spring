package cn.com.basic.jedis;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.*;
import redis.clients.util.SafeEncoder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaijiayi on 2018/12/25.
 */
@Slf4j
public class JedisTestEvalDemo {
    private static final Map<String, JedisPool> nodes = Maps.newConcurrentMap();

    /**
     * AOFEntry{aofHeader=AOFHeader{version=0, srcCluster='clustername_default_xx', srcSlot=13933, srcSlotVersion=0, srcMsTime=1572365428017, srcOpId=1692979825, opId=1692979825, srcDbId=0, flags=0}, aofOffset=[AOF: offset = 2099424900463, length=314],
     * command='EVAL', key='mcn.ccelebrity:updates:8:20191030_0',
     * parameters=[1, local v= redis.call('sadd',KEYS[1],ARGV[1]);redis.call('expire',KEYS[1],ARGV[2]);return v;, 69208, 172800], isAofContinue=false, entryReceiveTime=1572365469185}
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        HostAndPort hostAndPort = new HostAndPort("10.48.165.195", 6379);
        JedisPool jedisPool = genJedisPool(hostAndPort, null);
        Jedis jedis = jedisPool.getResource();
        Pipeline pipeline =jedis.pipelined();
        jedis.clusterSlots();
        jedis.clientSetname("JedisTestEvalDemo");
        Pipeline pipeline = jedis.pipelined();

        String key = "mcn.ccelebrity:updates:8:20191030_0";
        List<String> params = Lists.newArrayList();
        params.add("1");
        params.add("local v= redis.call('sadd',KEYS[1],ARGV[1]);redis.call('expire',KEYS[1],ARGV[2]);return v;");
        params.add("69208");
        params.add("172800");
        int keyCount = Integer.parseInt(params.get(0));
        String script = params.get(1);
        params.remove(1);
        params.add(1, key);
        List<String> argsA = params.subList(1, params.size());


        Response response = pipeline.eval(getBytes(script), keyCount, getBytes(argsA));
        pipeline.sync();
        System.out.println("target - " + response.get());

        jedisPool.close();
        while (!jedisPool.isClosed()) {
        }
        log.info("jedisPool closed!");
        TimeUnit.SECONDS.sleep(10000);
    }

    public static byte[][] getBytes(List<String> strList) {
        return SafeEncoder.encodeMany(strList.toArray(new String[strList.size()]));
    }

    public static byte[] getBytes(String str) {
        return SafeEncoder.encode(str);
    }

    public static JedisPool genJedisPool(HostAndPort hostAndPort, String auth) {
        String nodeKey = hostAndPort.getHost();
        if (!nodes.containsKey(nodeKey)) {
            try {
                JedisPool jedisPool = new JedisPool(JedisConfig.getDefaultPoolConfig(), hostAndPort.getHost(), hostAndPort.getPort(),
                        JedisConfig.DEFAULT_CONN_TIMEOUT, auth);
                nodes.put(nodeKey, jedisPool);
            } catch (Exception e) {
                log.error("JedisClusterBuilder|buildJedis = {} , exception = {}", nodeKey, e);
            }

        }

        return nodes.get(nodeKey);
    }


}
