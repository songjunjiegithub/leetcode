package javacode;

import java.util.*;

public class ZigzagIterator {
    Queue<List<Integer>> list_queue;
    Queue<Integer> index_queue;
    public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
        this.list_queue = new LinkedList<>();
        this.index_queue = new LinkedList<>();
        if (!v1.isEmpty()) {
            this.list_queue.add(v1);
            this.index_queue.add(0);
        }

        if (!v2.isEmpty()) {
            this.list_queue.add(v2);
            this.index_queue.add(0);
        }
    }

    public int next() {
        List<Integer> list = this.list_queue.poll();
        int index = this.index_queue.poll();
        int result = list.get(index);

        if (index < list.size() - 1) {
            index++;
            this.index_queue.add(index);
            this.list_queue.add(list);
        }

        return result;
    }

    public boolean hasNext() {
        return !list_queue.isEmpty();
    }

    public static void main(String[] args) {
        List<Integer> v1 = new ArrayList<>();
        List<Integer> v2 = new ArrayList<>();
        List<String> places = Arrays.asList("Buenos Aires", "Córdoba", "La Plata");
        ZigzagIterator zigzagIterator = new ZigzagIterator(Arrays.asList(1, 2), Arrays.asList(3, 4, 5, 6));
        System.out.println(zigzagIterator.next());
        System.out.println(zigzagIterator.next());
        System.out.println(zigzagIterator.next());
        System.out.println(zigzagIterator.next());
        System.out.println(zigzagIterator.next());
        System.out.println(zigzagIterator.next());
    }
}
