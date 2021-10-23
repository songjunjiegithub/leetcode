package javacode;

import java.util.HashSet;

/**
 * @author junjie
 * @date 2021年09月16日 13:23
 */
public class PhoneDirectory {
    HashSet<Integer> set;
    ListNode nodes;
    int inf;
    int sup;

    /** Initialize your data structure here
     @param maxNumbers - The maximum numbers that can be stored in the phone directory. */
    public PhoneDirectory(int maxNumbers) {
        set = new HashSet<>();
        inf = 0;
        sup = maxNumbers - 1;
        nodes = null;
    }

    /** Provide a number which is not assigned to anyone.
     @return - Return an available number. Return -1 if none is available. */
    public int get() {
        if (nodes == null && inf > sup) return -1;
        int result = 0;
        if (nodes != null) {
            result = nodes.val;
            nodes = nodes.next;
        } else {
            result = inf;
            ++inf;
        }
        set.add(result);

        return result;
    }

    /** Check if a number is available or not. */
    public boolean check(int number) {
        return !set.contains(number);
    }

    /** Recycle or release a number. */
    public void release(int number) {
        if (set.contains(number)) {
            set.remove(number);
            nodes = new ListNode(number, nodes);
        }
    }
}
