package producer;

import queue.MyQueue;

import java.util.List;

/**
 * Writer class
 *
 * @author  Jimmy Zhou
 * @version 1.0
 * @since   2020-07-29
 */
public class MyWriter implements Runnable {

    private List<Integer> nums;
    private MyQueue<Integer> myQueue;

    public MyWriter(MyQueue<Integer> myQueue, List<Integer> nums) {
        this.myQueue = myQueue;
        this.nums = nums;
    }

    @Override
    public void run() {
        myQueue.addAll(nums);
    }

    public void setNums(List<Integer> nums) {
        this.nums = nums;
    }
}
