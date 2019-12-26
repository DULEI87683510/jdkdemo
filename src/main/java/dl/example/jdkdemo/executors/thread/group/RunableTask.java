package dl.example.jdkdemo.executors.thread.group;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @className RunableTask
 * @description TODO
 * @author DL
 * @date 2019/12/25 14:42
 * @version 1.0
 */
@Slf4j
public class RunableTask implements Runnable {
    @Override
    public void run() {
        Random random =new Random();
      int sleepTime=  random.nextInt(1000);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("当前线程名：{},当前线程组名：{},睡眠:{}毫秒,",Thread.currentThread().getName(),Thread.currentThread().getThreadGroup().getName(),sleepTime);

    }
}
