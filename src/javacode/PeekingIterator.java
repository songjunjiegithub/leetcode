package javacode;

import java.util.Iterator;

class PeekingIterator implements Iterator<Integer> {
    Iterator<Integer> iterator;
    int next_val;
    boolean has_next;
    public PeekingIterator(Iterator<Integer> iterator) {
        // initialize any member here.
        this.iterator = iterator;
        this.has_next = iterator.hasNext();

        if (iterator.hasNext()) this.next_val = this.iterator.next();
    }

    // Returns the next element in the iteration without advancing the iterator.
    public Integer peek() {
        return this.next_val;
    }

    // hasNext() and next() should behave the same as in the Iterator interface.
    // Override them if needed.
    @Override
    public Integer next() {
        int tmp = this.next_val;
        this.has_next = this.iterator.hasNext();
        if (this.has_next) {
            this.next_val = this.iterator.next();
        }

        return tmp;
    }

    @Override
    public boolean hasNext() {
        return this.has_next;
    }
}
