package javacode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @author junjie
 * @date 2021年09月16日 16:58
 */
public class RandomizedSet {
    List<Integer> list;
    HashMap<Integer, Integer> map;
    /** Initialize your data structure here. */
    public RandomizedSet() {
        list = new ArrayList<>();
        map = new HashMap<>();
    }

    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if (!map.containsKey(val)) {
            list.add(val);
            map.put(val, list.size() - 1);
            return true;
        }

        return false;
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if (map.containsKey(val)) {
            int index = map.get(val);
            int lastIndex = list.size() - 1;
            if (index == lastIndex) {
                list.remove(lastIndex);
                map.remove(val);
            } else {
                int lastVal = list.get(lastIndex);
                list.set(index, lastVal);
                list.remove(lastIndex);
                map.remove(val);
                map.put(lastVal, index);
            }

            return true;
        }

        return false;
    }

    /** Get a random element from the set. */
    public int getRandom() {
        Random random = new Random();
        int index = random.nextInt(list.size());
        return list.get(index);
    }
}
