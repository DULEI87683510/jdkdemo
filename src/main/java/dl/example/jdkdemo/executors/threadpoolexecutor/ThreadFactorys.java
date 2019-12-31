package dl.example.jdkdemo.executors.threadpoolexecutor;/**
 * @ClassName ThreadFactorys
 * @Description TODO
 * @Author DL
 * @Date 2019/8/9 16:07
 * @Version 1.0
 */

import java.util.concurrent.ThreadFactory;

/**
 *@ClassName ThreadFactorys
 *@Description TODO
 *@Author DL
 *@Date 2019/8/9 16:07    
 *@Version 1.0
 */
public class ThreadFactorys implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }
}
