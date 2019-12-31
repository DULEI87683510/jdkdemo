package dl.example.jdkdemo.condition;

/**
 *@ClassName TestMain
 *@Description TODO
 *@Author DL
 *@Date 2019/8/12 15:33    
 *@Version 1.0
 */
public class TestMain {
    public static void main(String[] args) throws InterruptedException {
        //测试缓冲队列，最大长度为3

        BoundBuffer boundBuffer=new BoundBuffer(true);
        for (int i=1;i<=6;i++){
          new MyThread(i,boundBuffer).start();
        }


        Thread.sleep(2000);
        System.out.println( boundBuffer.take());
        Thread.sleep(2000);
        System.out.println( boundBuffer.take());
        Thread.sleep(2000);
        System.out.println( boundBuffer.take());
        Thread.sleep(2000);
        System.out.println( boundBuffer.take());
    }
    static class MyThread extends Thread{
        private int j;
        private BoundBuffer boundBuffer;
        public MyThread(int j,BoundBuffer boundBuffer){
            this.boundBuffer=boundBuffer;
            this.j=j;
        }
        @Override
        public void run() {
            boundBuffer.put(j);
            System.out.println("put["+j+"]"+"结束了");
        }
    }
}
