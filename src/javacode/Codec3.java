package javacode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author junjie
 * @date 2021年09月25日 17:06
 */
public class Codec3 {
    static class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    // Encodes a tree to a single string.
    public String serialize(Node root) {
        if (root == null) return "";

        StringBuilder sb = new StringBuilder();

        sb.append("[");
        toSerialize(root, sb);
        sb.append("]");

        return sb.toString();
    }

    public void toSerialize(Node root, StringBuilder sb) {
        if (root == null) return;

        sb.append(root.val);

        if (root.children != null && root.children.size() != 0) {
            sb.append("[");
            for (Node ele : root.children) {
                toSerialize(ele, sb);
                sb.append(" ");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("]");
        }
    }


    // Decodes your encoded data to tree.
    public Node deserialize(String data) {
        if (data.length() == 0) return null;

        Node head = new Node();
        int i = 1;
        Stack<Node> stack = new Stack<>();
        stack.add(head);
        Node ele = head;
        while (i < data.length()) {
            int j = i + 1;
            while (Character.isDigit(data.charAt(j))) j++;
            if (ele.children == null) ele.children = new ArrayList<>();
            List<Node> list = ele.children;
            list.add(new Node(Integer.parseInt(data.substring(i, j))));
            if (data.charAt(j) == '[') {
                stack.add(list.get(list.size() - 1));
                ele = stack.peek();
            } else if (data.charAt(j) == ']') {
                while (j < data.length() && data.charAt(j) == ']') {
                    stack.pop();
                    j++;
                }
                if (!stack.isEmpty()) ele = stack.peek();
            }
                i = j + 1;
        }

        return head.children.get(0);
    }


    public static void main(String[] args) {
        Codec3 codec3 = new Codec3();
        Node root = new Node(1, new ArrayList<Node>(){{
            add(new Node(2));
            add(new Node(3, new ArrayList<Node>(){{
                add(new Node(6));
                add(new Node(7, new ArrayList<Node>(){{
                    add(new Node(11, new ArrayList<Node>(){{
                        add(new Node(14));
                    }}));
                }}));
            }}));
            add(new Node(4, new ArrayList<Node>(){{
                add(new Node(8, new ArrayList<Node>(){{
                    add(new Node(12));
                }}));
            }}));
            add(new Node(5, new ArrayList<Node>(){{
                add(new Node(9, new ArrayList<Node>(){{
                    add(new Node(13));
                }}));
                add(new Node(10));
            }}));
        }});

//        System.out.println(codec3.serialize(root));
        System.out.println(codec3.serialize(codec3.deserialize(codec3.serialize(root))));
    }
}
