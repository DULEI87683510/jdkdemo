package dl.example.jdkdemo.lambda;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author DL
 * @description 流式计算
 * @date 2020/10/29
 */
@Slf4j
public class StreamDemo {
    private static long start = 1L;
    private static long end = 900_0000_0000L;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //计算1加到90亿
        //  method1();
        method2();
        //method3();


    }


    public static void method1() {

        long result = 0L;
        long startTime = System.currentTimeMillis();
        for (long i = start; i <= end; i++) {
            result += i;
        }
        long endTime = System.currentTimeMillis();
        System.out.println("结果：" + result + " 耗时：" + (endTime - startTime));
    }

    public static void method2() {
        long startTime = System.currentTimeMillis();
        //  Long sum = LongStream.rangeClosed(0L,end).parallel().sum();
        Long sum2 = LongStream.rangeClosed(1L, end).parallel().reduce(0L, Long::sum);
        long endTime = System.currentTimeMillis();
        System.out.println("结果：" + sum2 + " 耗时：" + (endTime - startTime));

    }

    public static void method3() throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(4);
        List<CompletableFuture<Long>> copyOnWriteArrayList = new ArrayList<>();
        CopyOnWriteArrayList resultList = new CopyOnWriteArrayList();
        CompletableFuture<Long> longCompletableFuture = CompletableFuture.supplyAsync(() -> {
            long result = 0L;
            for (long i = 0L; i <= 45_0000_0000L; i++) {
                result += i;
            }
            resultList.add(result);
            return result;
        }).whenComplete((aLong, throwable) -> countDownLatch.countDown());


        CompletableFuture<Long> longCompletableFuture2 = CompletableFuture.supplyAsync(() -> {
            long result = 0L;
            for (long i = 45_0000_0001L; i <= end; i++) {
                result += i;
            }
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resultList.add(result);
            return result;
        }).whenComplete((aLong, throwable) -> countDownLatch.countDown());
        copyOnWriteArrayList.add(longCompletableFuture);
        copyOnWriteArrayList.add(longCompletableFuture2);

        CompletableFuture<Long> longCompletableFuture3 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resultList.add(0L);
            return 0L;
        }).whenComplete((a, b) -> countDownLatch.countDown());
        copyOnWriteArrayList.add(longCompletableFuture3);
        CompletableFuture<Long> longCompletableFuture4 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resultList.add(0L);
            return 0L;
        }).whenComplete((a, b) -> countDownLatch.countDown());
        copyOnWriteArrayList.add(longCompletableFuture4);
    /*    for (int i = 0; i < 4; i++) {
            CompletableFuture<Long> longCompletableFuture5 = CompletableFuture.supplyAsync(() -> {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 0L;
            }).whenComplete((a, b) -> countDownLatch.countDown());
            copyOnWriteArrayList.add(longCompletableFuture5);
        }*/

        //countDownLatch.await();

        CompletableFuture<Long>[] completableFutures = copyOnWriteArrayList.stream().toArray(CompletableFuture[]::new);
        CompletableFuture<Void> all = CompletableFuture.allOf(completableFutures);
        all.join();
        long count = resultList.stream().mapToLong(result -> (long) result).sum();
        Long result = count;
        long endTime = System.currentTimeMillis();
        System.out.println("结果：" + result + " 耗时：" + (endTime - startTime));
    }

}
