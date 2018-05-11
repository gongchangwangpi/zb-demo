package com.books.gaobingfa.d6;

/**
 * Created by books on 2017/5/9.
 */
public class StreamTest {

    static Thread current;

    public static void main(String[] args) {
        int[] arr = {1, 2, 4, 5, 6, 3, 0};

        /*Arrays.stream(arr).forEach((x)->System.out.print(x));
        System.out.println();
//        Arrays.stream(arr).forEach(StreamTest::add);
        Arrays.stream(arr).parallel().forEach(StreamTest::add);
        System.out.println();*/
//        System.out.println(current);
    }

    public static void add(int i) {
        i++;
        current = Thread.currentThread();
//        System.out.println(Thread.currentThread());
        System.out.print(i);
    }
}
