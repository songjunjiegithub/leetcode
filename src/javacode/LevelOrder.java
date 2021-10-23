package javacode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LevelOrder {
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) return new LinkedList<>();

        Queue<TreeNode> treeNodes = new LinkedList<>();
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> level = new ArrayList<>();
        treeNodes.offer(root);
        treeNodes.offer(null);
        while (!treeNodes.isEmpty()) {
            TreeNode head = treeNodes.poll();
            if (head != null) {
                level.add(head.val);
                if (head.left != null) treeNodes.offer(head.left);
                if (head.right != null) treeNodes.offer(head.right);
            } else {
                result.add(level);
                level = new ArrayList<>();
                if (!treeNodes.isEmpty()) treeNodes.offer(null);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        LevelOrder levelOrder = new LevelOrder();
        System.out.println(levelOrder.levelOrder(root));
//        System.out.println("hello world");
    }
}
