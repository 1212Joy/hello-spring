package cn.com.basic.thread;

/**
 * Created by ZhaiJiaYi on 2016/7/15.
 */
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+ " : hello my runnable is running!");
    }
}
