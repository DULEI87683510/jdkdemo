package dl.example.jdkdemo.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *@ClassName BoundBuffer
 *@Description 缓冲队列得实现(类似于消息队列,先进先出的一种数据结构)
 *@Author DL
 *@Date 2019/8/12 15:11    
 *@Version 1.0
 */
public class BoundBuffer {
    /**
     * 锁对象
     */
    final Lock lock=new ReentrantLock();
    /**
     * 写线程条件
     */
    final Condition notFull = lock.newCondition();
    /**
     * 读线程条件
     */
    final Condition notEmpty = lock.newCondition();
    /**
     * 初始化一个长度为100的队列
     */
    final Object[] items = new Object[3];
    /**
     * 写索引
     */
    int putptr;
    /**
     * 读索引
     */
    int takeptr;
    /**
     * 队列中存在的数据个数
     */
    int count;

    boolean autoDelet;

    public BoundBuffer(boolean autoDelet){
        this.autoDelet=autoDelet;
    }
    public void put (Object o){

        lock.lock(); //获取锁
        try {
            while (count == items.length) {
                // 当计数器count等于队列的长度时，不能再插入，因此等待。阻塞写线程。
                notFull.await();
            }
            //赋值
            items[putptr] = o;
            putptr++;

            if (putptr == items.length){
                // 若写索引写到队列的最后一个位置了，将putptr置为0。
                putptr = 0;
            }

            count++; // 每放入一个对象就将计数器加1。
            notEmpty.signal(); // 一旦插入就唤醒取数据线程。
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock(); // 最后释放锁
        }

    }


    public Object take() throws InterruptedException {
        lock.lock(); // 获取锁
        try {
            while (count == 0) {
                // 如果计数器等于0则等待，即阻塞读线程。
                notEmpty.await();
            }
            // 取值
            Object x = items[takeptr];
            //当数据被取出后，是否删除
            if(autoDelet){
                items[takeptr]=null;
            }
            takeptr++;
            if (takeptr == items.length) {
                //若读锁应读到了队列的最后一个位置了，则读锁应置为0；即当takeptr达到队列长度时，从零开始取
                takeptr = 0;
            }
            // 每取一个将计数器减1。
            count++;
            //每取走一个就唤醒存线程。
            notFull.signal();
            return x;
        } finally {
            // 释放锁
            lock.unlock();
        }
    }

}
