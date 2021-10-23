package javacode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @author junjie
 * @date 2021年09月16日 17:26
 */
public class RandomizedCollection {
    HashMap<Integer, ListNode> map;
    List<Integer> list;

    /** Initialize your data structure here. */
    public RandomizedCollection() {
        map = new HashMap<>();
        list = new ArrayList<>();
    }

    /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
    public boolean insert(int val) {
        ListNode ele = map.getOrDefault(val, null);
        while (ele != null && (ele.val >= list.size() || list.get(ele.val) != val)) ele = ele.next;

        ele = new ListNode(list.size(), ele);
        map.put(val, ele);
        list.add(val);

        return ele.next == null;
    }

    /** Removes a value from the collection. Returns true if the collection contained the specified element. */
    public boolean remove(int val) {
        ListNode ele = map.getOrDefault(val, null);
        while (ele != null && (ele.val >= list.size() || list.get(ele.val) != val)) ele = ele.next;
        if (ele == null) return false;

        int index = ele.val;
        ele = ele.next;
        if (ele == null) {
            map.remove(val);
        } else {
            map.put(val, ele);
        }

        int lastIndex = list.size() - 1;
        int lastVal = list.get(lastIndex);
        list.set(index, lastVal);
        list.remove(list.size() - 1);
        if (index != lastIndex) {
            ele = map.getOrDefault(lastVal, null);
            ele = new ListNode(index, ele);
            map.put(lastVal, ele);
        }

        return true;
    }

    /** Get a random element from the collection. */
    public int getRandom() {
        Random random = new Random();
        int index = random.nextInt(list.size());
        return list.get(index);
    }


}
