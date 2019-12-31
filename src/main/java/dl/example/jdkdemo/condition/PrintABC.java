package dl.example.jdkdemo.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *@ClassName PrintABC
 *@Description 多线程依次打印ABC，每个打印10次
 *@Author DL
 *@Date 2019/8/12 16:39    
 *@Version 1.0
 */
public class PrintABC {
    private Lock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();
    /**
     * 内部状态
     */
    private String type = "A";

    /**
     * 方法的基本要求为：
     * 1、该方法必须为原子的。
     * 2、当前状态必须满足条件。若不满足，则等待；满足，则执行业务代码。
     * 3、业务执行完毕后，修改状态，并唤醒指定条件下的线程。
     */
    public void printA() {
        //锁，保证了线程安全。
        lock.lock();
        try {
            while (type != "A") {
                //type不为A，
                try {
                    //将当前线程阻塞于conditionA对象上，将被阻塞。
                    conditionA.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //type为A，则执行。
            System.out.println(Thread.currentThread().getName() + " 正在打印A");
            //将type设置为B。
            type = "B";
            //唤醒在等待conditionB对象上的一个线程。将信号传递出去。
            conditionB.signal();
        } finally {
            //解锁
            lock.unlock();
        }
    }

    public void printB() {
        lock.lock(); //锁
        try {
            while (type != "B") {
                //type不为B，
                try {
                    //将当前线程阻塞于conditionB对象上，将被阻塞。
                    conditionB.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //type为B，则执行。
            System.out.println(Thread.currentThread().getName() + " 正在打印B");
            //将type设置为C。
            type = "C";
            //唤醒在等待conditionC对象上的一个线程。将信号传递出去。
            conditionC.signal();
        } finally {
            //解锁
            lock.unlock();
        }
    }

    public void printC() {
        lock.lock(); //锁
        try {
            while (type != "C") {
                try {
                    conditionC.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(Thread.currentThread().getName() + " 正在打印C");
            type = "A";
            conditionA.signal();
        } finally {
            lock.unlock(); //解锁
        }
    }
}




