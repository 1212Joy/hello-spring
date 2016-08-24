package cn.com.basic.design.singleton;

/**
 * Created by ZhaiJiaYi on 2016/8/2.
 */
public class Singleton {
    private static Singleton instance = null;
    private Singleton(){}
    //在多线程操作中是不安全的，后果是可能会产生多个Singleton对象
  /*  public static Singleton getInstance() {
        if(instance == null)
            instance = new Singleton();
        return instance;
    }*/

    //对于绝大部分不需要同步的情况来说，synchronized 会让函数执行效率糟糕一百倍以上
  /*  public static synchronized Singleton getInstance() {
        if(singleton == null)
            instance = new Singleton();
        return instance;
    }*/

    //double-checked（双重检测）的方法
    public static Singleton getInstance() {
        if (instance == null){
            synchronized (Singleton.class){
                if (instance == null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
