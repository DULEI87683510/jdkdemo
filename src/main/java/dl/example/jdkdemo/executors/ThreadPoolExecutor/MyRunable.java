package dl.example.jdkdemo.executors.ThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;

/**
 *@ClassName MyRunable
 *@Description TODO
 *@Author DL
 *@Date 2019/8/9 16:46    
 *@Version 1.0
 */
@Slf4j
public class MyRunable implements Runnable {

    private int taskNum;


    public MyRunable(int num) {

        this.taskNum = num;

    }


    @Override

    public void run() {


        log.info("ThreadName:"+Thread.currentThread().getName()+"正在执行task " + taskNum);
    /*    try {

           // Thread.sleep(10000);

        } catch (InterruptedException e) {

            e.printStackTrace();

        }*/

        log.info("task " + taskNum + "执行完毕");

    }
}
