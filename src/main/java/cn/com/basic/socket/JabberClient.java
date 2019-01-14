package cn.com.basic.socket;
import cn.com.basic.Utils.FileUtil;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.net.Socket;
/**
 * Created by ZhaiJiaYi on 2016/7/13.
 */


/**
 ***************************************************************
 * 项目名称：JavaThread 程序名称：JabberClient 日期：2012-8-23 下午01:47:12
 * 作者：
 * 模块：
 * 描述：
 * 备注：
 * ------------------------------------------------------------
 * 修改历史
 * 序号
 * 日期
 * 修改人
 * 修改原因
 * 修改备注：
 *
 * @version
 ***************************************************************
 */
public class JabberClient {

    /**
     * 方法名：main 描述： 作者：白鹏飞 日期：2012-8-23 下午01:47:12
     *
     * @param @param args
     * @return void
     */
    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader br = null;
        PrintWriter pw = null;
        try {
            //客户端socket指定服务器的地址和端口号
            socket = new Socket("127.0.0.1", 6380);//JabberServer.PORT
            System.out.println("Socket=" + socket);

            //读取本地aof并发送请求
            File aofFile = new File("/Users/joy/source/redis-boot/db/appendonly_test.aof");
            String aofContent = FileUtil.readFileToString(aofFile);

            //根据redis协议-分割命令

            //同服务器原理一样
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

            for (int i = 0; i < 1; i++) {
                //发送请求字节信息
                String requestContent= new String(aofContent) ;
                System.out.println("req:"+requestContent);
                pw.println(requestContent);
                pw.flush();
                //获取来自无服务端的数据
                String str = br.readLine();
                System.out.println("res:"+str);
            }
            pw.println("END");
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("close......");
                br.close();
                pw.close();
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        Jedis jedis = new Jedis("127.0.0.1",6479);
        System.out.println("dbSize: "+jedis.dbSize());
    }

}