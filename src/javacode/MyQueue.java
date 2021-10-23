package javacode;

import java.util.Stack;

class MyQueue {
    Stack<Integer> fifo;
    Stack<Integer> lifo;

    /** Initialize your data structure here. */
    public MyQueue() {
        this.fifo = new Stack<>();
        this.lifo = new Stack<>();
    }

    /** Push element x to the back of queue. */
    public void push(int x) {
        this.lifo.add(x);
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        if (this.fifo.size() == 0) {
            while (this.lifo.size() != 0) this.fifo.add(this.lifo.pop());
        }

        return this.fifo.pop();
    }

    /** Get the front element. */
    public int peek() {
        if (this.fifo.size() == 0) {
            while (this.lifo.size() != 0) this.fifo.add(this.lifo.pop());
        }

        return this.fifo.peek();
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        return this.fifo.isEmpty() && this.lifo.isEmpty();
    }
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */
