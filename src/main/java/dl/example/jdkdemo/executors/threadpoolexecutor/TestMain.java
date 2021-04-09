package dl.example.jdkdemo.executors.threadpoolexecutor;


import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.*;

import static java.util.concurrent.Executors.*;

/**
 * @author DL
 * @version 1.0
 * @className TestMain
 * @description TODO
 * @date 2019/12/26 17:06
 */

/**
 * 该主方法还配置了jvvm参数
 * 死循环
 * -Xmx8m 最大堆内存
 * -Xms8m 初始堆内存
 * -Xmn1m 新生代内存
 * -Xss512k 栈内存
 * -XX:+PrintGC 打印gc信息
 * -XX:+PrintGCDetails gc详情
 * -XX:+HeapDumpOnOutOfMemoryError  OOM之时导出堆镜像到文件
 * -XX:+HeapDumpPath  导出OOM文件的路径设置(与上一个参数需要同时给)
 * -XX:OnOutOfMemoryError 在OOM时，执行一个脚本
 * 示例如下：-XX:OnOutOfMemoryError=D:/tools/jdk1.7_40/bin/printstack.bat %p在发生OOM之时，发送邮件或者重启应用等动作。
 */
public class TestMain {
    public static void main(String[] args) throws InterruptedException {

        byte[] bytes = new byte[1024 * 1024 * 7];

/*//Thread.sleep(10000);
        String str="2";
        while (true){
            str+=str;
        }*/

        /*
         *//**
         * 手动创建线程池
         * execute()方法实际上是Executor中声明的方法，在ThreadPoolExecutor进行了具体的实现，
         * 这个方法是ThreadPoolExecutor的核心方法，
         * 通过这个方法可以向线程池提交一个任务，交由线程池去执行。
         *submit()方法是在ExecutorService中声明的方法，在AbstractExecutorService就已经有了具体的实现，
         * 在ThreadPoolExecutor中并没有对其进行重写，这个方法也是用来向线程池提交任务的，但是它和execute()方法不同，它能够返回任务执行的结果，去看submit()方法的实现，
         *会发现它实际上还是调用的execute()方法，只不过它利用了Future来获取任务执行结果。
         *submit()方法中参数可以是Callable类型也可以是Runnable类型，而execute()方法参数只能是Runnable类型。
         *//*
        ThreadPoolExecutor threadPoolExecutor = ThreadPool.getThreadPool();
        for (int i = 0; i < 5; i++) {
            MyRunable myRunable = new MyRunable(i);
            threadPoolExecutor.execute(myRunable);

        }
        threadPoolExecutor.shutdown();


        *//**
         * (固定数目线程的线程池)
         * newFixedThreadPool线程池特点
         *核心线程数和最大线程数大小一样
         *没有所谓的非空闲时间，即keepAliveTime为0
         *阻塞队列为无界队列LinkedBlockingQueue
         *
         * 1、提交任务
         *2、如果线程数少于核心线程，创建核心线程执行任务
         *3、如果线程数等于核心线程，把任务添加到LinkedBlockingQueue阻塞队列
         *4、如果线程执行完任务，去阻塞队列取任务，继续执行
         *
         * 因为阻塞队列为无界队列LinkedBlockingQueue，所以任务大量堆积的话，会导致OOM异常
         *
         * 使用场景
         *FixedThreadPool 适用于处理CPU密集型的任务，
         *确保CPU在长期被工作线程使用的情况下，
         *尽可能的少的分配线程，即适用执行长期的任务。
         *//*
        ExecutorService fixedThreadPoolService = newFixedThreadPool(2);

        for (int i=0;i<10000;i++){
            fixedThreadPoolService.submit(new MyRunable(i));
        }




        *//**
         * (可缓存线程的线程池)
         * 核心线程数为0
         *最大线程数为Integer.MAX_VALUE
         *阻塞队列是SynchronousQueue
         *非核心线程空闲存活时间为60秒
         *当提交任务的速度大于处理任务的速度时，每次提交一个任务，就必然会创建一个线程。极端情况下会创建过多的线程，耗尽 CPU 和内存资源。
         * 由于空闲 60 秒的线程会被终止，长时间保持空闲的 CachedThreadPool 不会占用任何资源
         *
         *1、提交任务
         *2、因为没有核心线程，所以任务直接加到SynchronousQueue队列。
         *3、判断是否有空闲线程，如果有，就去取出任务执行。
         *4、如果没有空闲线程，就新建一个线程执行。
         *5、执行完任务的线程，还可以存活60秒，如果在这期间，接到任务，可以继续活下去；否则，被销毁。
         *
         *使用场景
         *用于并发执行大量短期的小任务
         *//*
        ExecutorService cachedThreadPoolService = newCachedThreadPool();
        for (int i=0 ;i<5;i++){
            cachedThreadPoolService.submit(new MyRunable(2));
        }

        *//**
         * (单线程的线程池)
         * 核心线程数为1
         *最大线程数也为1
         *阻塞队列是LinkedBlockingQueue
         *keepAliveTime为0
         *
         * 因为阻塞队列同样的是LinkedBlockingQueue，所以任务堆积太大同样也会导致OOM
         *
         *1、提交任务
         *2、线程池是否有一条线程在，如果没有，新建线程执行任务
         *3、如果有，将任务加到阻塞队列
         *4、当前的唯一线程，从队列取任务，执行完一个，再继续取，一个人（一条线程）夜以继日地干活。
         *
         * 适用于串行执行任务的场景，一个任务一个任务地执行。
         *//*
        ExecutorService singleThreadExecutorService = newSingleThreadExecutor();
        for (int i=0;i<5;i++){
            singleThreadExecutorService.submit(new MyRunable(3));
        }



        *//**
         * (定时及周期执行的线程池)
         * 最大线程数为Integer.MAX_VALUE
         *阻塞队列是DelayedWorkQueue
         *keepAliveTime为0
         *scheduleAtFixedRate() ：按某种速率周期执行
         *scheduleWithFixedDelay()：在某个延迟后执行
         *
         *
         *
         * 1、添加一个任务
         *2、线程池中的线程从 DelayQueue 中取任务
         *3、线程从 DelayQueue 中获取 time 大于等于当前时间的task
         *4、执行完后修改这个 task 的 time 为下次被执行的时间
         *5、这个 task 放回DelayQueue队列中
         *//*
        //
        ScheduledExecutorService scheduledExecutorService = newScheduledThreadPool(1);

        scheduledExecutorService.scheduleWithFixedDelay(new MyRunable(4),1,10, TimeUnit.SECONDS);
        scheduledExecutorService.shutdownNow();*/


        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10,Integer.MAX_VALUE,1000,TimeUnit.MILLISECONDS,new ArrayBlockingQueue<>(10),defaultThreadFactory(),new ThreadPoolExecutor.DiscardPolicy());
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        String jvmName = runtimeBean.getName();
        System.out.println("JVM Name = " + jvmName);
        long pid = Long.valueOf(jvmName.split("@")[0]);
        System.out.println("JVM PID  = " + pid);
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        for (int i = 0; i < 10000; i++) {
            threadPool.execute(() -> {
                System.out.println("当前线程总数为：" + bean.getThreadCount());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        TimeUnit.SECONDS.sleep(10);
        System.out.println("线程总数为 = " + bean.getThreadCount());
    }
}
