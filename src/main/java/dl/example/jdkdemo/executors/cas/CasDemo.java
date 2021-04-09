package dl.example.jdkdemo.executors.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author DL
 * @description CAS的示例
 * @date 2020/11/4
 */
public class CasDemo {
    /**
     * CAS compare And Set 比较期望值，如果是你的期望值，就去更新值。
     **/
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(1, 1);
    static AtomicInteger atomicInteger = new AtomicInteger(1);

    public static void main(String[] args) {
        //test1();
        //test2();
        test3();

    }

    /**
     * 经典ABA问题
     */
    public static void test1() {
        //修改成B

        System.out.println(atomicInteger.compareAndSet(1, 2));
        //修改为A
        System.out.println(atomicInteger.compareAndSet(2, 1));

        //期待A，改为c，这种情况下是可以修改成功的
        System.out.println(atomicInteger.compareAndSet(1, 3));
    }

    /**
     * 解决ABA问题 ，乐观锁,加版本号去解决
     */
    public static void test2() {
        //修改成B
        System.out.println(atomicStampedReference.compareAndSet(1, 2,
                atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));
        //修改回A
        int stamp = atomicStampedReference.getStamp();
        System.out.println(atomicStampedReference.compareAndSet(2, 1,
                stamp, stamp + 1));

        //修改C
        System.out.println(atomicStampedReference.compareAndSet(1, 3,
                stamp, stamp + 1));


    }

    /**
     * 自旋锁
     */
    static class MyLock {
        AtomicReference<String> atomicReference = new AtomicReference<>("a");

        public void lock() {
            System.out.println(Thread.currentThread().getName() + "->lock");
            //自旋锁
            while (!atomicReference.compareAndSet("a", "b")) {
            }
        }

        public void unLock() {
            System.out.println(Thread.currentThread().getName() + "->unLock");
            while (!atomicReference.compareAndSet("b", "a")) {
            }
        }
    }

    /**
     * 测试自旋锁
     */
    public static void test3() {
        MyLock mylock = new MyLock();
        Lock lock = new ReentrantLock();

        new Thread(() -> {
            lock.lock();
            lock.lock();

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
             lock.unlock();
             lock.unlock();
            }
        }, "A线程").start();
        new Thread(() -> {

            mylock.lock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                mylock.unLock();
            }
        }, "B线程").start();
    }
    /**
     * 死锁排查
     * jps -l 查看进程号
     * jstack  进程号
     */
}
