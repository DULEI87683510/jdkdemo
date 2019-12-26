package dl.example.jdkdemo.executors.ThreadPoolExecutor;

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
public class ThreadPool {
    private static  ThreadPoolExecutor executor;

    public static synchronized ThreadPoolExecutor getThreadPool() {
        if (null == executor) {
            executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5), new ThreadFactorys(), new Reject());
        }
        return executor;
    }

}