package dl.example.jdkdemo.executors.queue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author DL
 * @description java队列的使用
 * @date 2020/10/29
 * <p>
 * <p>
 * 抛出异常	特殊值	阻塞	超时
 * 插入	add(e)	offer(e)	put(e)	offer(e, time, unit)
 * 移除	remove()	poll()	take()	poll(time, unit)
 * 检查	element()	peek()	不可用	不可用
 */
public class QueueDemo {

    public static void main(String[] args) throws InterruptedException {
        //  test1();
        //test2();
        test3();
    }

    //抛出异常
    public static void test1() {
        //定义一个初始长度为3的队列,add超过3会抛出异常
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        //检查是否有头元素，有就返回头元素，如果为空则抛出异常

        System.out.println(queue.add("a"));
        System.out.println(queue.element());
        System.out.println(queue.add("b"));
        System.out.println(queue.add("c"));
        //  System.out.println(queue.add("d"));

        System.out.println(queue.remove("a"));
        System.out.println(queue.remove("b"));
        System.out.println(queue.remove("c"));
        System.out.println(queue.remove("d"));


    }

    //不抛出异常
    public static void test2() {
        //定义一个初始长度为3的队列,add超过3会抛出异常
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        //加上时间和单位，可以超时等待，如果超时则返回
        System.out.println(queue.offer("a"));
        System.out.println(queue.offer("b"));
        System.out.println(queue.offer("c"));
        //不会抛出异常
        System.out.println(queue.offer("d"));
        //加上时间和单位，可以超时等待，如果超时则返回空
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        //取出头元素并返回，如果没有就返回null
        System.out.println(queue.poll());
    }

    //阻塞
    public static void test3() throws InterruptedException {
        //定义一个初始长度为3的队列,add超过3会抛出异常
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        queue.put("a");
        queue.put("b");
        queue.put("c");
        //当队列被塞满之后，会被阻塞，直到改元素put进去
        // queue.put("d");

        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
        //当队列没有元素可以取出的时候，该线程会被阻塞，直到取出元素为止
        System.out.println(queue.take());

    }

}
