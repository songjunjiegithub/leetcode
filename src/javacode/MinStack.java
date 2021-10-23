package javacode;

class MinStack {
    class Node {
        int val;
        Node next;
    }

    long min;
    Node head;

    /** initialize your data structure here. */
    public MinStack() {
        this.min = (long)Integer.MAX_VALUE + 1;
        head = null;
    }

    public void push(int val) {
        this.min = Math.min(this.min, val);
        Node tmp = new Node();
        tmp.val = val;
        tmp.next = head;
        head = tmp;
    }

    public void pop() {
        if (head == null) return;

        if (this.min == head.val) {
            this.min = (long) Integer.MAX_VALUE + 1;
            Node ele = head.next;
            while (ele != null) {
                this.min = Math.min(this.min, ele.val);
                ele = ele.next;
            }
        }

        head = head.next;
    }

    public int top() {
        return head == null ? null : head.val;
    }

    public long getMin() {
        return this.min == (long) Integer.MAX_VALUE + 1 ? null : this.min;
    }

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
    }
}
