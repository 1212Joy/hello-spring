package cn.com.basic.redis;

import cn.com.basic.Utils.FileUtil;
import redis.clients.jedis.Jedis;

import java.io.File;

public class ReplicationTest {

    public static void main(String[] args) {

        //读取aof文件
        File aofFile = new File("/Users/joy/source/redis-boot/db/appendonly.aof");
        String aofContent = FileUtil.readFileToString(aofFile);
       // System.out.println(aofContent);

        //根据redis协议解析文件
        String[] aofContentArray = aofContent.split( "\r\n");
        System.out.println(aofContentArray.length);
        for (String s:aofContentArray) {
            System.out.println(s);
        }


    }

   private void handelDBFileByJedis(){
        //jedis-api:https://static.javadoc.io/redis.clients/jedis/2.9.0/overview-summary.html

       //读取rdb文件
       File rdbFile = new File("/Users/joy/source/redis-boot/db/dump.rdb");
       byte[] rdbContent = FileUtil.readFileToStream(rdbFile);
       System.out.println(rdbContent.toString());

       //读取aof文件
       File aofFile = new File("/Users/joy/source/redis-boot/db/appendonly.aof");
       byte[] aofContent = FileUtil.readFileToStream(aofFile);
       System.out.println(aofContent);

   }
}
