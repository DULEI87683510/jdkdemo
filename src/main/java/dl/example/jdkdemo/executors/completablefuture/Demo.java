package dl.example.jdkdemo.executors.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author DL
 * @description
 * @date 2020/7/29
 */
@Slf4j
public class Demo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future=CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(3000);
              log.info( Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           return  "我完成了";
        });
        log.info(future.get());
      LocalDateTime localDateTime=  LocalDateTime.parse("2020-07-29T09:13:58");
      log.info(String.valueOf(localDateTime.getHour()));
      CompletableFuture.allOf();

    }
}
