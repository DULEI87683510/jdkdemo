package dl.example.jdkdemo.lambda;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * @author DL
 * @description
 * @date 2020/11/2
 */
public class ForkJoinSumDemo extends RecursiveTask<Long> {

    //子任务处理数组的起始位置和终止位置
    private final Long start;
    private final Long end;

    //不再将任务分解为子任务的数组大小
    public static final long THRESHOLD = 10_000;

    //公共构造函数用于创建主任务
    public ForkJoinSumDemo(long numbers) {
        this(0L, numbers);
    }

    //私有构造函数用于以递归方式为主任务创建子任务
    private ForkJoinSumDemo(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    //覆盖RecursiveTask抽象方法，负责求和的部分的大小
    @Override
    protected Long compute() {
        Long length = end - start;
        if (length <= THRESHOLD) {
            //顺序计算结果
            return computeSequentially();
        }
        //创建一个子任务来为数组的前一半求和
        ForkJoinSumDemo leftTask = new ForkJoinSumDemo(start, start + length / 2);
        //利用另一个ForkJoinPool线程异步执行新建的子任务
        leftTask.fork();
        //创建一个子任务来为数组的后一半求和
        ForkJoinSumDemo rightTask = new ForkJoinSumDemo(start + length / 2, end);
        //同步执行第二个子任务，有可能运行进一步递归划分
        Long rightResult = rightTask.compute();
        //读取第一个子任务的结果，如果尚未完成就等待
        Long leftResult = leftTask.join();
        //该任务的结果是两个子任务结果的组合
        return leftResult + rightResult;
    }

    //在子任务不再可分时，计算结果的简单算法
    private long computeSequentially() {
        long sum = 0;
        for (Long i = start; i <= end; i++) {
            sum += i;
        }
        return sum;
    }

    //对前n个自然数求和的方法
    public static long forkJoinSum(long end) {

        ForkJoinTask<Long> task = new ForkJoinSumDemo(end);
        return new ForkJoinPool().invoke(task);
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Long result = forkJoinSum(900_0000_0000L);
        long endTime = System.currentTimeMillis();
        System.out.println("结果：" + result + " 耗时：" + (endTime - startTime));
    }
}
