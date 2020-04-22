package dl.example.jdkdemo.sort;

import java.util.Arrays;

/**
 * @className BubbleSort
 * @description TODO
 * @author DL
 * @date 2020/1/6 14:13
 * @version 1.0
 */

public class BubbleSort {
    /**
     * @param arr 需要排序的数组
     * @param asc 是否升序
     * @return 排序后的数组
     */
    public static void sort(int[] arr,boolean asc){
        for (int i = 0; i < arr.length; i++) {
            for (int j=i+1;j<arr.length;j++){
                if(asc){
                    if(arr[i]>arr[j]){
                        //升序
                        swap(arr, i, j);
                    }
                }else{
                    if(arr[i]<arr[j]){
                        //降序
                        swap(arr, i, j);
                    }
                }
            }
        }
    }

    /**
     * 交换数组中两个元素的位置
     */
    private static void swap(int[] arr, int i, int j) {
        int temp= arr[i];
        arr[i] =arr[j];
        arr[j]=temp;
    }

    public static void main(String[] args) {
        int[] arr=new int[]{1,5,6,8,9,4,3};
        System.out.println("排序数组："+Arrays.toString(arr));
        sort(arr,true);
        System.out.println("升序排列："+ Arrays.toString(arr));
        sort(arr,false);
        System.out.println("降序排列："+Arrays.toString(arr));
    }
}
