package dl.example.jdkdemo.executors.threadpoolexecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ThreadPool
 * @Description TODO
 * @Author DL
 * @Date 2019/8/9 15:38
 * @Version 1.0
 */

/***
 * ArrayBlockingQueue
 ArrayBlockingQueue（有界队列）是一个用数组实现的有界阻塞队列，按FIFO排序量。


 LinkedBlockingQueue（可设置容量队列）基于链表结构的阻塞队列，按FIFO排序任务，容量可以选择进行设置，不设置的话，将是一个无边界的阻塞队列，
 最大长度为Integer.MAX_VALUE，吞吐量通常要高于ArrayBlockingQuene；newFixedThreadPool线程池使用了这个队列

 DelayQueue
 DelayQueue（延迟队列）是一个任务定时周期的延迟执行的队列。根据指定的执行时间从小到大排序，否则根据插入到队列的先后排序。newScheduledThreadPool线程池使用了这个队列。

 PriorityBlockingQueue
 PriorityBlockingQueue（优先级队列）是具有优先级的无界阻塞队列；

 SynchronousQueue
 SynchronousQueue（同步队列）一个不存储元素的阻塞队列，每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态，吞吐量通常要高于LinkedBlockingQuene，newCachedThreadPool线程池使用了这个队列。
 */
public class ThreadPool {
    private static ThreadPoolExecutor executor;

    public static synchronized ThreadPoolExecutor getThreadPool() {
        if (null == executor) {
            /**
             * 参数：
             *corePoolSize： 线程池核心线程数最大值
             *maximumPoolSize： 线程池最大线程数大小
             *keepAliveTime： 线程池中非核心线程空闲的存活时间大小
             *unit： 线程空闲存活时间单位
             *workQueue： 存放任务的阻塞队列
             *threadFactory： 用于设置创建线程的工厂，可以给创建的线程设置有意义的名字，可方便排查问题。
             * handler： 线程池的饱和策略事件，主要有四种类型。
             */
            executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5), new ThreadFactorys(), new Reject());
        }
        return executor;
    }

}