package cn.com.basic.redis;

import cn.com.basic.Utils.FileUtil;
import com.sun.org.apache.xpath.internal.operations.String;

import java.io.File;

public class ReplicationTest {

    public static void main(String[] args){
        //读取rdb文件
        File rdbFile = new File("/Users/joy/source/redis-boot/db/dump.rdb");
        byte[] rdbContent = FileUtil.readFileToStream(rdbFile);
        System.out.println(rdbContent.toString());

        //读取aof文件
        File aofFile = new File("/Users/joy/source/redis-boot/db/appendonly.aof");
        byte[] aofContent = FileUtil.readFileToStream(aofFile);
        System.out.println(aofContent);



    }

    void handelDBFileByJedis(){
        //jedis-api:https://static.javadoc.io/redis.clients/jedis/2.9.0/overview-summary.html

    }
}
