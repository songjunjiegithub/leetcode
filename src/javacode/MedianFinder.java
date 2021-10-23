package javacode;

import java.util.PriorityQueue;

public class MedianFinder {
    PriorityQueue<Integer> p_min;
    PriorityQueue<Integer> p_max;

    /** initialize your data structure here. */
    public MedianFinder() {
        this.p_min = new PriorityQueue<>();
        this.p_max = new PriorityQueue<>(((o1, o2) -> o2 - o1));
    }

    public void addNum(int num) {
        if (this.p_max.size() > this.p_min.size()) {
            this.p_max.add(num);
            this.p_min.add(this.p_max.poll());
        } else {
            this.p_min.add(num);
            this.p_max.add(this.p_min.poll());
        }
    }

    public double findMedian() {
        if (this.p_max.size() == this.p_min.size()) return (double) (this.p_min.peek() + this.p_max.peek()) / 2;
        return (double) this.p_max.peek();
    }

    public static void main(String[] args) {
        MedianFinder medianFinder = new MedianFinder();
        medianFinder.addNum(1);
        medianFinder.addNum(2);
        medianFinder.addNum(3);
        System.out.println(medianFinder.findMedian());
    }
}
