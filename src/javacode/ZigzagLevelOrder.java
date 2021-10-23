package javacode;

import java.util.*;

public class ZigzagLevelOrder {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) return new LinkedList<>();

        Queue<TreeNode> treeNodes = new LinkedList<>();
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> level = new ArrayList<>();
        Boolean isReversal = false;
        treeNodes.offer(root);
        treeNodes.offer(null);
        while (!treeNodes.isEmpty()) {
            TreeNode head = treeNodes.poll();
            if (head != null) {
                level.add(head.val);
                if (head.left != null) treeNodes.offer(head.left);
                if (head.right != null) treeNodes.offer(head.right);
            } else {
                if (isReversal) Collections.reverse(level);
                isReversal = !isReversal;
                result.add(level);
                level = new ArrayList<>();
                if (!treeNodes.isEmpty()) treeNodes.offer(null);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        ZigzagLevelOrder levelOrder = new ZigzagLevelOrder();
        System.out.println(levelOrder.zigzagLevelOrder(root));
//        System.out.println("hello world");
    }
}
