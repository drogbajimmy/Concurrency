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
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
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

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(40, queue.size());

        Callable<List<Integer>> callable01 = new MyReader(queue);
        Callable<List<Integer>> callable02 = new MyReader(queue);
        Callable<List<Integer>> callable03 = new MyReader(queue);
        Future<List<Integer>> future01 = executor.submit(callable01);
        Future<List<Integer>> future02 = executor.submit(callable02);
        Future<List<Integer>> future03 = executor.submit(callable03);

        while(!(future01.isDone() && future02.isDone() && future03.isDone())) {
            System.out.println("Reading...");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        List<Integer> list01 = new ArrayList<>();
        List<Integer> list02 = new ArrayList<>();
        List<Integer> list03 = new ArrayList<>();

        try {
            list01 = future01.get();
            list02 = future02.get();
            list03 = future03.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        List<Integer> result = new ArrayList<>(list01);
        result.addAll(list02);
        result.addAll(list03);

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
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
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

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(0, queue.size());

        Callable<List<Integer>> callable01 = new MyReader(queue);
        Callable<List<Integer>> callable02 = new MyReader(queue);
        Callable<List<Integer>> callable03 = new MyReader(queue);
        Future<List<Integer>> future01 = executor.submit(callable01);
        Future<List<Integer>> future02 = executor.submit(callable02);
        Future<List<Integer>> future03 = executor.submit(callable03);

        while(!(future01.isDone() && future02.isDone() && future03.isDone())) {
            System.out.println("Reading...");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        List<Integer> list01 = new ArrayList<>();
        List<Integer> list02 = new ArrayList<>();
        List<Integer> list03 = new ArrayList<>();

        try {
            list01 = future01.get();
            list02 = future02.get();
            list03 = future03.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        List<Integer> result = new ArrayList<>(list01);
        result.addAll(list02);
        result.addAll(list03);

        int[] arr = result.stream().mapToInt(i -> i).toArray();
        assertEquals(0, arr.length);

        executor.shutdown();
    }
}
