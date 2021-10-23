package javacode;

import java.util.HashMap;

public class LRUCache {
    class MNode {
        int value;
        int key;
        MNode next;
        MNode pre;
        public MNode(int key, int value) {
            this.value = value;
            this.key = key;
        }
    }

    int capacity;
    HashMap<Integer, MNode> map = new HashMap<>();
    MNode head;
    MNode tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public void updateNode(MNode ele) {
        if (ele != head) {
            if (ele == tail) tail = ele.pre;
            ele.pre.next = ele.next;
            if (ele.next != null) ele.next.pre = ele.pre;
            ele.next = head;
            head.pre = ele;
            ele.pre = null;
            head = ele;
        }
    }

    public void insertNode(MNode ele) {
        if (head == null) {
            head = ele;
            tail = ele;
        } else {
            ele.next = head;
            head.pre = ele;
            head = ele;
        }
    }

    public int deleteNode() {
        int key = tail.key;
        tail = tail.pre;
        if (tail == null) {
            head = null;
        } else {
            tail.next = null;
        }
        return key;
    }

    public int get(int key) {
        if (map.get(key) == null) return -1;

        MNode ele = map.get(key);

        updateNode(ele);

        return ele.value;
    }

    public void put(int key, int value) {
        if (map.get(key) != null) {
            MNode ele = map.get(key);
            ele.value = value;
            updateNode(ele);
        } else {
            if (map.size() == capacity) {
                map.remove(deleteNode());
            }

            map.put(key, new MNode(key, value));
            insertNode(map.get(key));
        }
    }

    public static void main(String[] args) {
        LRUCache lRUCache = new LRUCache(2);
        lRUCache.put(1, 1); // 缓存是 {1=1}
        lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
        System.out.println(lRUCache.get(1));    // 返回 1
        lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
        System.out.println(lRUCache.get(2));    // 返回 -1 (未找到)
        lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
        System.out.println(lRUCache.get(1));    // 返回 -1 (未找到)
        System.out.println(lRUCache.get(3));    // 返回 3
        System.out.println(lRUCache.get(4));    // 返回 4
    }
}
