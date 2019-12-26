package dl.example.jdkdemo.executors.ThreadPoolExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @className TestMain
 * @description TODO
 * @author DL
 * @date 2019/12/26 17:06
 * @version 1.0
 */

public class TestMain {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor= ThreadPool.getThreadPool();
        for (int i=0 ;i<5;i++){
            MyRunable myRunable=new MyRunable(i);
            threadPoolExecutor.execute(myRunable);
        }
        threadPoolExecutor.shutdown();

    }
}
