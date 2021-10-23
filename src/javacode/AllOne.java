package javacode;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author junjie
 * @date 2021年09月26日 09:51
 */
public class AllOne {
    private static class Node {
        List<String> val;
        Node left;
        Node right;

    }

    Node head;
    HashMap<String, Integer> map;
    /** Initialize your data structure here. */
    public AllOne() {
        head = new Node();
        map = new HashMap<>();
    }

    /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
    public void inc(String key) {
        if (map.containsKey(key)) {
            int v = map.get(key);
            map.put(key, v + 1);
            delete(key, v);
            add(key, v + 1);
        } else {
            map.put(key, 1);
            add(key, 1);
        }
    }

    /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
    public void dec(String key) {
        if (map.containsKey(key)) {
            int v = map.get(key);
            if (v == 1) {
                map.remove(key);
                delete(key, v);
            } else {
                map.put(key, v - 1);
                delete(key, v);
                add(key, v - 1);
            }
        }
    }

    /** Returns one of the keys with maximal value. */
    public String getMaxKey() {
        if (map.isEmpty()) return "";
        Node ele = head;
        for (int i = 30; i >= 0; i--) {
            if (ele.right != null) {
                ele = ele.right;
            } else {
                ele = ele.left;
            }
        }

        return ele.val.get(0);
    }

    /** Returns one of the keys with Minimal value. */
    public String getMinKey() {
        if (map.isEmpty()) return "";
        Node ele = head;
        for (int i = 30; i >= 0; i--) {
            if (ele.left != null) {
                ele = ele.left;
            } else {
                ele = ele.right;
            }
        }

        return ele.val.get(0);
    }

    public void add(String key, int v) {
        Node ele = head;
        for (int i = 30; i >= 0; i--) {
            if ((v & (1 << i)) != 0) {
                if (ele.right == null) ele.right = new Node();
                ele = ele.right;
            } else {
                if (ele.left == null) ele.left = new Node();
                ele = ele.left;
            }
        }

        if (ele.val == null) ele.val = new ArrayList<>();
        List<String> list = ele.val;

        list.add(key);
    }

    public void delete(String key, int v) {
        Node ele = head;
        Node del = head;
        int delI = 30;

        for (int i = 30; i >= 0; i--) {
            if (ele.left != null && ele.right != null) {
                del = ele;
                delI = i;
            }

            if ((v & (1 << i)) != 0) {
                ele = ele.right;
            } else {
                ele = ele.left;
            }
        }

        if (ele.val.size() > 1) {
            for (int i = 0; i < ele.val.size(); i++) {
                if (ele.val.get(i).equals(key)) {
                    ele.val.remove(i);
                    return;
                }
            }
        } else {
            if ((v & (1 << delI)) != 0) {
                del.right = null;
            } else {
                del.left = null;
            }
        }
    }

    public static void main(String[] args) {
        AllOne allOne = new AllOne();
        allOne.inc("hello");
        allOne.inc("hello");
        System.out.println(allOne.getMaxKey());
        System.out.println(allOne.getMinKey());
        allOne.inc("leet");
        System.out.println(allOne.getMaxKey());
        System.out.println(allOne.getMinKey());

    }


}
