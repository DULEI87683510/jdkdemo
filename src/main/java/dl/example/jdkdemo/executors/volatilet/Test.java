package dl.example.jdkdemo.executors.volatilet;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author DL
 * @description volatile
 * @date 2020/11/3
 */
public class Test {
    //不加volatile test1 程序会死循环
    /**
     * 1.保证可见性 线程a修改值，线程b会立即知道
     * 2.不保证原子性 比如说做++操作
     * 3.禁止指令重排 （编译后的执行顺序的重排）
     */
    private volatile static int num = 0;

    public static void add() {
        num++;
    }

    public static void test1() throws InterruptedException {
        new Thread(() -> {
            while (num == 0) {

            }
        }).start();

        TimeUnit.SECONDS.sleep(2);
        num = 1;
    }

    //计算的结果不一定每次都是20000，所以解决方案是加锁synchronized，ReentrantLock,或者使用原子类AtomicInteger
    public static void test2() throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    add();
                }
            }).start();
        }

        while (Thread.activeCount() > 2) { //main gc
            Thread.yield();
        }

        System.out.println(num);
    }

    public static void main(String[] args) throws InterruptedException {
        // test1();
        test2();

    }
}
