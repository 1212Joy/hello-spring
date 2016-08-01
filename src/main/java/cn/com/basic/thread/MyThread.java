package cn.com.basic.thread;

/**
 * Created by ZhaiJiaYi on 2016/7/15.
 */
public class MyThread extends Thread{

    @Override
    public void run() {
       System.out.print("hello my thread is running!");
    }
}
