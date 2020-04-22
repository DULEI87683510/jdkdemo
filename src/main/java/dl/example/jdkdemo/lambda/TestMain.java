package dl.example.jdkdemo.lambda;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 *@ClassName TestMain
 *@Description TODO
 *@Author DL
 *@Date 2020/1/15 16:31    
 *@Version 1.0
 */
public class TestMain {

    public static void main(String[] args) {
        List<Apple> appleList=new ArrayList<>();
        appleList.add(new Apple("red"));
        appleList.add(new Apple("blue"));
        appleList.add(new Apple("black"));
        List<String> list=getList(appleList, Apple::getColor);
        System.out.println(list);

    }
    public static List<String> getList(List<Apple> apples,Function<Apple,String> f){
        List<String> list=new ArrayList<>();
        for (Apple a:apples  ) {
           list.add(f.apply(a)) ;
        }
       return list;
    }
}
