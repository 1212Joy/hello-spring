package cn.com.basic.thread;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by Joy on 2017/4/9.
 */
public class Producer2 implements Runnable{
    private PriorityQueue<Integer> queue = null;
    private int queueSize =0;
    private Lock lock = null;
    private Condition consume=null;
    private Condition produce=null;

    public Producer2(PriorityQueue<Integer> queue,int queueSize,Lock lock,Condition produce,Condition consume){
        this.queue=queue;
        this.queueSize=queueSize;
        this.lock=lock;
        this.consume=consume;
        this.produce=produce;
    }

    public void product(){
        while(true){
            lock.lock();
            try{
                while(queue.size()==queueSize){
                    System.out.println("队列满了，等待消费者消费...");
                    try {
                        produce.await();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        consume.signal();
                    }
                }
                queue.offer(1);
                System.out.println("向队列中插入了一个对象，队列的剩余空间是："+(queueSize-queue.size()));
                consume.signal();
                System.out.println("线程：Thread.currentThread().getName()-是否拥有queue"+Thread.holdsLock(queue));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }finally{
                lock.unlock();
            }
        }
    }

    @Override
    public void run() {
        this.product();
    }
}
