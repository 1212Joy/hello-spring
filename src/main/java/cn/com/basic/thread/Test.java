package cn.com.basic.thread;

import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Joy on 2017/4/9.
 */
public class Test {
    public static void main(String[] args) {
        int queueSize = 20;
        //这里可以回忆一下JVM中多线程共享内存的知识
        PriorityQueue<Integer> queue = new PriorityQueue<>(queueSize);
//
//        Consumer consumer = new Consumer(queue);
//        Producer producer = new Producer(queue, queueSize);
//
//        new Thread(consumer).start();
//        new Thread(producer).start();

        Lock lock = new ReentrantLock();

        Condition produce = lock.newCondition();
        Condition consume = lock.newCondition();

        Consumer2 consumer2 = new Consumer2(queue,lock,produce,consume);
        Producer2 producer2 = new Producer2(queue, queueSize,lock,produce,consume);
        new Thread(consumer2).start();
        new Thread(producer2).start();


    }
}
