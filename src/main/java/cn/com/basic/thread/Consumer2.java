package cn.com.basic.thread;

/**
 * Created by Joy on 2017/4/9.
 */
import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Consumer2 implements Runnable{
    private PriorityQueue<Integer> queue = null;
    private Lock lock = null;
    private Condition consume=null;
    private Condition produce=null;


    public Consumer2(PriorityQueue<Integer> queue,Lock lock,Condition produce,Condition consume){
        this.queue=queue;
        this.lock =lock;
        this.consume = consume;
        this.produce = produce;
    }

    private void consume(){
        while(true){
            lock.lock();
            try{
                while(queue.size()==0){
                    System.out.println("队列为空，等待数据...");
                    try {
                        consume.await();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        produce.signal();
                    }
                }
                queue.poll();
                System.out.println("从队列中取出一个元素，队列剩余数量是："+queue.size());
                produce.signal();
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
        this.consume();
    }

}
