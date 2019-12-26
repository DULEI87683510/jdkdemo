package dl.example.jdkdemo.executors.thread.group;/**
 * @className TestMain2
 * @description TODO
 * @author DL
 * @date 2019/12/26 17:36
 * @version 1.0
 */

import lombok.extern.slf4j.Slf4j;

/**
 *@ClassName TestMain2
 *@Description TODO
 *@Author DL
 *@Date 2019/12/26 17:36    
 *@Version 1.0
 */
@Slf4j
public class TestMain2 {
    public static void main(String[] args) {
        new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("睡眠休时");
        }).start();
    }
}
