package javacode;

import java.util.*;

public class Test {
    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public Node copyRandomList(Node head) {
        HashMap<Node, Node> map = new HashMap<>();

        return copyRandomList(head, map);
    }

    public Node copyRandomList(Node head, HashMap<Node, Node> map) {
        if (head == null) return head;

        if (map.get(head) == null) {
            map.put(head, new Node(head.val));
            map.get(head).next = copyRandomList(head.next, map);
            map.get(head).random = copyRandomList(head.random, map);
        }

        return map.get(head);
    }

    // 打印为二进制形式
    public String bitPrint(int num) {
        StringBuffer stringBuffer = new StringBuffer();
        while (num != 0) {
            stringBuffer.append(num & 1);
            num = num / 2;
        }

        return stringBuffer.reverse().toString();
    }

    public int unique(String[] arr) {
        HashSet<String> set = new HashSet<>();
        set.addAll(Arrays.asList(arr));

        return set.size();
    }


    public static void main(String[] args) {
        Test test = new Test();
//        String beginWord = "hit", endWord = "cog";
//        List<String> wordList = Arrays.asList("hot","dot","dog","lot","log","cog");
//        System.out.println(test.findLadders(beginWord, endWord, wordList));
//        String s1 = "a";
//        String s2 = "b";
//        System.out.println(s1 + s2);

//        Node one = new Node(1);
//        Node two = new Node(2);
//        Node three = new Node(3);
//        Node four = new Node(4);
//        one.neighbors.add(two);
//        one.neighbors.add(four);
//        two.neighbors.add(one);
//        two.neighbors.add(three);
//        three.neighbors.add(two);
//        three.neighbors.add(four);
//        four.neighbors.add(one);
//        four.neighbors.add(three);
//        test.cloneGraph(one);
//        System.out.println(test.bitPrint(468));
    }
}
