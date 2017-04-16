package cn.com.basic.simple;


import cn.com.basic.thread.Consumer;
import cn.com.basic.thread.MyRunnable;
import cn.com.basic.thread.MyThread;
import cn.com.basic.thread.Producer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhaijiayi on 2016/3/10.
 */

public class JavaTest {

    @Test
    public void Thread() throws Exception {

        ThreadPoolExecutor myPool = new ThreadPoolExecutor(new MyRunnable());
    }

}


        //volatile CAS AtomicInteger


