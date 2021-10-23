package javacode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author junjie
 * @date 2021年09月16日 18:55
 */
// This is the interface that allows for creating nested lists.
// You should not implement it, or speculate about its implementation
public class NestedInteger {
    private Integer val;
    private List<NestedInteger> list;
    // Constructor initializes an empty nested list.
    public NestedInteger() {
        list = null;
        val = null;
    }

    // Constructor initializes a single integer.
    public NestedInteger(int value) {
        val = value;
    }

    // @return true if this NestedInteger holds a single integer, rather than a nested list.
    public boolean isInteger() {
        return val == null;
    }

    // @return the single integer that this NestedInteger holds, if it holds a single integer
    // Return null if this NestedInteger holds a nested list
    public Integer getInteger() {
        return val;
    }

    // Set this NestedInteger to hold a single integer.
    public void setInteger(int value) {
        list = null;
        val = value;
    }

    // Set this NestedInteger to hold a nested list and adds a nested integer to it.
    public void add(NestedInteger ni) {
        if (list == null) {
            list = new ArrayList<>();
            val = null;
        }
        list.add(ni);
    }

    // @return the nested list that this NestedInteger holds, if it holds a nested list
    // Return empty list if this NestedInteger holds a single integer
    public List<NestedInteger> getList() {
        return list == null ? new ArrayList<>() : list;
    }
}

