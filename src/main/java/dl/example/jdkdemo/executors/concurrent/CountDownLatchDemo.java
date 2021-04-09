package dl.example.jdkdemo.executors.concurrent;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author DL
 * @description
 * @date 2020/10/13
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName());
                countDownLatch.countDown();//计数器-1
            }, String.valueOf(i)).start();
        }
        countDownLatch.await();//等待计数器归零
        System.out.println("归零开始执行任务");

        //模拟多线程并发请求
        CountDownLatch countDownLatch2 = new CountDownLatch(10);
        Map<String, Object> map = new ConcurrentHashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            final int temp = i;
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "等待中");
                    countDownLatch2.await();//等待计数器归零
                    //doSomeThing，请求
                    map2.put("temp", temp);
                    list.add(temp + "");
                    System.out.println(Thread.currentThread().getName() + "执行了put");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
            countDownLatch2.countDown();//每次-1，直到计数器归零
        }

    }

    //加法计数器
    static class CyclicBarrierDemo {
        public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
            CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> System.out.println("召唤神龙"));//计数器直到8，才会执行该方法
            for (int i = 1; i <= 7; i++) {
                final int temp = i;
                new Thread(() -> {
                    try {
                        System.out.println("集齐第" + temp + "龙珠");
                        cyclicBarrier.await();//计数器+1
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }, String.valueOf(i)).start();

            }
        }

    }

    //信号量
    static class SemaphoreDemo {
        public static void main(String[] args) {
            Semaphore semaphore = new Semaphore(3);
            for (int i = 1; i <= 6; i++) {
                final int temp = i;
                new Thread(() -> {
                    try {
                        semaphore.acquire();
                        System.out.println("抢到车位" + Thread.currentThread().getName());
                        TimeUnit.SECONDS.sleep(2);
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }, temp + "").start();
            }

        }


    }
}
