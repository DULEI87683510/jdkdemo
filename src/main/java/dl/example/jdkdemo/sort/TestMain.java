package dl.example.jdkdemo.sort;

import java.util.Arrays;

/**
 * @className TestMain
 * @description TODO
 * @author DL
 * @date 2020/1/6 14:19
 * @version 1.0
 */

public class TestMain {

    public static void main(String[] args) {
         int [] arry={2,9,54,74,6,3,3,5,3,5,78,5,7,5,4,3,12,34,5,6,4,4,3,5,3,6,43};
         insertSort(arry);
    }
    private static void insertSort( int [] arry){
        //插入排序
        //有序区索引
        int orderLastIndex=0;
        //循环无序区
        for (int i = orderLastIndex+1; i < arry.length; i++) {
            //得到循环得索引得值
            int temp=arry[i];
            //在有序区中插入索引得位置,刚开始就是自己得位置
            int insertIndex=i;
            //循环有序区
            for (int j=orderLastIndex;j>=0;j--){
                if(temp>arry[j]){
                    arry[j+1]=arry[j];
                    insertIndex--;
                }
            }
            orderLastIndex++;
            arry[insertIndex]=temp;
        }
        System.out.println( Arrays.toString(arry));

    }
}
