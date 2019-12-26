package dl.example.jdkdemo.executors.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *@ClassName Reject
 *@Description TODO
 *@Author DL
 *@Date 2019/8/9 16:24    
 *@Version 1.0
 */

/**
 *ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
 *ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
 *ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
 *ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务
 */
public class Reject implements RejectedExecutionHandler {
    private static final Logger log = LoggerFactory.getLogger(Reject.class);
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        log.info("ThreadName:"+"线程池拒绝该任务");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

    }
}
