package consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Reader class
 *
 * @author  Jimmy Zhou
 * @version 1.0
 * @since   2020-07-29
 */
public class MyReader implements Callable<List<Integer>> {

    private ConcurrentLinkedQueue<Integer> myQueue;

    public MyReader(ConcurrentLinkedQueue<Integer> myQueue) {
        this.myQueue = myQueue;
    }

    @Override
    public List<Integer> call() throws Exception {

        List<Integer> nums = new ArrayList<>();

        while (!myQueue.isEmpty()) {
            Integer element = myQueue.poll();

            if (element != null) {
                nums.add(element);
            }
        }

        return nums;
    }
}
