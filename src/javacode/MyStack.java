package javacode;

import java.util.LinkedList;
import java.util.Queue;

class MyStack {
    Queue<Integer> fifo;
    Queue<Integer> lifo;
    /** Initialize your data structure here. */
    public MyStack() {
        this.fifo = new LinkedList<>();
        this.lifo = new LinkedList<>();
    }

    /** Push element x onto stack. */
    public void push(int x) {

    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        return 0;
    }

    /** Get the top element. */
    public int top() {
        return 0;
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {
        return this.lifo.size() + this.fifo.size() == 0;
    }
}
