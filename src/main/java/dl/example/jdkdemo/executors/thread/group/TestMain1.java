package dl.example.jdkdemo.executors.thread.group;

import lombok.extern.slf4j.Slf4j;



/**
 *@ClassName TestMain
 *@Description TODO
 *@Author DL
 *@Date 2019/12/25 14:38    
 *@Version 1.0
 */
@Slf4j
public class TestMain1 {
    public static void main(String[] args) throws InterruptedException {

        ThreadGroup threadGroup=new ThreadGroup("dl");

            for (int i=0;i<5;i++){
                Thread thread=new Thread(threadGroup,new RunableTask(),"dl-"+i);
                thread.start();
            }


        //Thread.currentThread().setDaemon(true);


        //  Thread.currentThread().join();
            //返回此线程组中活动线程的数目
         int count=    threadGroup.activeCount();

        log.info("活动线程数目：{}",count);
           //中断线程组所有的线程
            threadGroup.interrupt();
            //返回此线程组是否为后台线程组
          boolean isDaemon=  threadGroup.isDaemon();
            log.info("线程组是否为后台线程组：{}",isDaemon);
           //设置此线程组是否为后台线程组
        //    threadGroup.setDaemon(true);
     log.info("主线程是否为Daemon:{}",Thread.currentThread().isDaemon());

    }

}
