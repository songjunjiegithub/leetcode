package javacode;

import java.util.HashMap;
import java.util.Stack;

public class Codec2 {
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) return "";

        StringBuilder result = new StringBuilder();

        TreeNode ele = root;
        Stack<TreeNode> stack = new Stack<>();

        HashMap<Integer, Integer> map = new HashMap<>();
        while (!stack.isEmpty() || ele != null) {
            while (ele != null) {
                stack.add(ele);
                ele = ele.left;
            }

            ele = stack.pop();
            map.put(ele.val, map.getOrDefault(ele.val, 0) + 1);
            ele.val = (ele.val < 0 ? -1 : 1) * (Math.abs(ele.val) + (map.get(ele.val) << 10));
            result.append(ele.val).append("#");
            ele = ele.right;
        }

        result.deleteCharAt(result.length() - 1);
        result.append("$");

        ele = root;
        while (!stack.isEmpty() || ele != null) {
            while (ele != null) {
                result.append(ele.val);
                result.append("#");
                ele.val = (ele.val < 0 ? -1 : 1) * (Math.abs(ele.val) & 1023);
                stack.add(ele);
                ele = ele.left;
            }

            ele = stack.pop().right;
        }

        result.deleteCharAt(result.length() - 1);

        return result.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data.equals("")) return null;

        String[] tmpStr = data.split("\\$");
        String[] preamble_order = tmpStr[0].split("#");
        String[] middle_order = tmpStr[1].split("#");

        return dfs(preamble_order, middle_order, 0, preamble_order.length - 1, 0, middle_order.length - 1);
    }

    public TreeNode dfs(String[] preamble_order, String[] middle_order, int p_start, int p_end, int m_start, int m_end) {
        if (p_start > p_end) return null;
        String s = middle_order[m_start];
        TreeNode root = new TreeNode((Integer.parseInt(s) < 0 ? -1 : 1) * (Math.abs(Integer.parseInt(s)) & 1023));
        if (p_start == p_end) return root;

        int num = 0;
        for (int i = p_start; i <= p_end; i++) {
            if (preamble_order[i].equals(s)) {
                num = i;
                break;
            }
        }

        root.left = dfs(preamble_order, middle_order, p_start, num - 1, m_start + 1, num - p_start + m_start);
        root.right = dfs(preamble_order, middle_order, num + 1, p_end, num - p_start + m_start + 1, m_end);

        return root;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1, new TreeNode(2), new TreeNode(3, new TreeNode(4), new TreeNode(5)));

        Codec2 codec2 = new Codec2();

//        System.out.println(codec2.serialize(root));
        System.out.println(codec2.deserialize(codec2.serialize(root)));
    }
}
