package queue;

import consumer.MyReader;
import org.junit.Test;
import producer.MyWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;

/**
 * Queue test class
 *
 * @author  Jimmy Zhou
 * @version 1.0
 * @since   2020-07-29
 */
public class QueueTest {

    @Test
    public void testPushAndPop() {
        MyQueue<Integer> queue = new MyQueue<>();
        List<Integer> list1 = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> list2 = Arrays.asList(10, 11, 12, 13, 14, 15, 16, 17, 18, 19);
        List<Integer> list3 = Arrays.asList(20, 21, 22, 23, 24, 25, 26, 27, 28, 29);
        List<Integer> list4 = Arrays.asList(30, 31, 32, 33, 34, 35, 36, 37, 38, 39);

        MyWriter writer01 = new MyWriter(queue, list1);
        MyWriter writer02 = new MyWriter(queue, list2);
        MyWriter writer03 = new MyWriter(queue, list3);
        MyWriter writer04 = new MyWriter(queue, list4);

        ExecutorService executor = Executors.newCachedThreadPool();

        Thread t1 = new Thread(writer01, "producer1");
        Thread t2 = new Thread(writer02, "producer2");
        Thread t3 = new Thread(writer03, "producer3");
        Thread t4 = new Thread(writer04, "producer4");

        executor.execute(t1);
        executor.execute(t2);
        executor.execute(t3);
        executor.execute(t4);

        List<Integer> result = new ArrayList<>();
        int totalSize = list1.size() + list2.size() + list3.size() + list4.size();
        while(result.size() < totalSize) {
            MyReader reader = new MyReader(queue);
            List<Integer> tmpResult = reader.read();
            result.addAll(tmpResult);
        }

        int[] arr = result.stream().mapToInt(i -> i).toArray();
        assertEquals(40, arr.length);
        Arrays.sort(arr);
        for (int i=0; i<arr.length; ++i) {
            assertEquals(i, arr[i]);
        }

        executor.shutdown();
    }

    @Test
    public void testPopFromEmptyQueue() {
        MyQueue<Integer> queue = new MyQueue<>();
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        List<Integer> list3 = new ArrayList<>();
        List<Integer> list4 = new ArrayList<>();


        MyWriter writer01 = new MyWriter(queue, list1);
        MyWriter writer02 = new MyWriter(queue, list2);
        MyWriter writer03 = new MyWriter(queue, list3);
        MyWriter writer04 = new MyWriter(queue, list4);

        ExecutorService executor = Executors.newCachedThreadPool();

        Thread t1 = new Thread(writer01, "producer1");
        Thread t2 = new Thread(writer02, "producer2");
        Thread t3 = new Thread(writer03, "producer3");
        Thread t4 = new Thread(writer04, "producer4");

        executor.execute(t1);
        executor.execute(t2);
        executor.execute(t3);
        executor.execute(t4);

        List<Integer> result = new ArrayList<>();
        int totalSize = list1.size() + list2.size() + list3.size() + list4.size();
        while(result.size() < totalSize) {
            MyReader reader = new MyReader(queue);
            List<Integer> tmpResult = reader.read();
            result.addAll(tmpResult);
        }

        int[] arr = result.stream().mapToInt(i -> i).toArray();
        assertEquals(0, arr.length);

        executor.shutdown();
    }
}
