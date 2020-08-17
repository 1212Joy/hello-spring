package cn.com.basic.jedis;

import com.google.common.collect.Lists;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by zhaijiayi on 2018/12/25.
 */
public class JedisFirstDemo {
    public static void main(String[] args) {
        
        //先启动redis 服务端
        Set<String> keys = null;
        List<String> keyStr = Lists.newArrayList();
        try (Jedis jedis = new Jedis("127.0.0.1", 6479)) {

            keys = jedis.keys("*");


        }
        //查询100个key
        int[] lengthArray = {10, 20, 50, 100, 200, 400};

        for (int length : lengthArray) {
            //int length = 50;
            System.out.println("----------开始执行，元素个数=" + length + "-------------");
            for (int i = 1; i < 4; i++) {

                System.out.println("第" + i + "次执行");

                if (keys.size() > length) {
                    keyStr = keys.stream().collect(Collectors.toList());
                    Collections.shuffle(keyStr);
                }
                List<String> targetList = keyStr.subList(0, length);
                //   System.out.println("第" + i + "次执行，key列表："+ JSON.toJSONString(targetList));
                multiGetByMGet(targetList);
                multiGetByPGet(targetList);
            }
        }


    }

    public static void multiGetByMGet(List<String> keys) {

        String[] keyArray = new String[keys.size()];
        keys.toArray(keyArray);
        try (Jedis jedis = new Jedis("127.0.0.1", 6479)) {
            long startTime = System.nanoTime();

            List<String> result = jedis.mget(keyArray);


            //System.out.println("multiGetByMGet:rto = " + BigDecimal.valueOf((System.nanoTime() - startTime) / 1000000, 2) + "毫秒");
            BigDecimal rate = BigDecimal.valueOf(Double.valueOf((System.nanoTime() - startTime)) / Double.valueOf(1000000));
            System.out.println("multiGetByMGet:rto = " + rate.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "豪秒");
        }
    }


    public static void multiGetByPGet(List<String> keys) {

        List<Response<String>> responseList = Lists.newArrayList();
        long startTime = 0L;
        Pipeline pipeline = null;
        try (Jedis jedis = new Jedis("127.0.0.1", 6479)) {
            pipeline = jedis.pipelined();
            startTime = System.nanoTime();
            for (String key : keys) {
                Response<String> response = pipeline.get(key);
                responseList.add(response);
            }
            pipeline.sync();

        } finally {
            BigDecimal rate = BigDecimal.valueOf(Double.valueOf((System.nanoTime() - startTime)) / Double.valueOf(1000000));
            System.out.println("multiGetByPGet:rto = " + rate.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "豪秒");
            try {
                pipeline.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
