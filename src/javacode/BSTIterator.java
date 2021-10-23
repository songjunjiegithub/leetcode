package javacode;

import java.util.ArrayList;
import java.util.Stack;

public class BSTIterator {
    Stack<TreeNode> stack;
    public BSTIterator(TreeNode root) {
        stack = new Stack<>();
        while (root != null) {
            stack.add(root);
            root = root.left;
        }
    }

    public int next() {
        TreeNode ele = stack.pop();
        TreeNode r = ele.right;
        while (r != null) {
            stack.add(r);
            r = r.left;
        }

        return ele.val;
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }
}
