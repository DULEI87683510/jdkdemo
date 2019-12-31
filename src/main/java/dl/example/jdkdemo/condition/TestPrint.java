package dl.example.jdkdemo.condition;

/**
 *@ClassName TestPrint
 *@Description TODO
 *@Author DL
 *@Date 2019/8/14 14:34    
 *@Version 1.0
 */
public class TestPrint {

    public static void main(String[] args) {
        //业务对象。
        final PrintABC business = new PrintABC();
        //线程1号，打印10次A。
        Thread ta = new Thread(() -> {
            for(int i=0;i<10;i++){
                business.printA();
            }
        });
        //线程2号，打印10次B。
        Thread tb = new Thread(() -> {
            for(int i=0;i<10;i++){
                business.printB();
            }
        });
        //线程3号，打印10次C。
        Thread tc = new Thread(() -> {
            for(int i=0;i<10;i++){
                business.printC();
            }
        });
        //执行3条线程。
        ta.start();
        tb.start();
        tc.start();
    }
}
