package dl.example.jdkdemo.executors.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author DL
 * @description
 * @date 2020/11/9
 */
public class Demo1WriteAndReadTxt {
    public static void main(String[] args) throws IOException {
        copy();
    }


    public static void write() throws IOException {
        String str = "hello dl";
        FileOutputStream fileOutputStream = new FileOutputStream("d:/1.txt");
        FileChannel channel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);
        byteBuffer.put(str.getBytes());
        //由写转换为读
        byteBuffer.flip();
        channel.write(byteBuffer);
        fileOutputStream.close();
    }

    public static void read() throws IOException {

        File file = new File("d:/1.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel channel = fileInputStream.getChannel();
        //byteBuffer初始时是写入
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);
        channel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array(), StandardCharsets.UTF_8));
    }

    public static void copy() throws IOException {
        File file = new File("d:/1.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel channel = fileInputStream.getChannel();
        //byteBuffer初始时是写入状态
        // ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        ByteBuffer byteBuffer = ByteBuffer.allocate(40);
        FileOutputStream fileOutputStream = new FileOutputStream("d:/1copy.txt");
        FileChannel outputStreamChannel = fileOutputStream.getChannel();
        while (true) {
            //将bytebuffer的limit参数重置为改buffer的最大长度，指针归为初始位置position=0
            byteBuffer.clear();
            int read = channel.read(byteBuffer);//返回的值表示通道数据数据写到了byteBuffer的哪个位置
            //-1表示读完了
            if (read == -1) {
                break;
            }
            byteBuffer.flip();
            outputStreamChannel.write(byteBuffer);//这个操作会读取buffer，会将position置为读取到的位置，
            // 若文件长度本身比buffer小，则为文件长度，
            // 若文件长度bibuffer大，则只能读到limit值大小的位置，故limit和position的值相等。read的返回值就不会是-1。

        }
        fileInputStream.close();
        outputStreamChannel.close();

    }
}
