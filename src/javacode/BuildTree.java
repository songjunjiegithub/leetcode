package javacode;

public class BuildTree {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildTree(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
    }

    public TreeNode buildTree(int[] preorder, int[] inorder, int preorder_start_index, int preorder_end_index, int inorder_start_index, int inorder_end_index) {
        if(preorder_start_index > preorder_end_index || inorder_start_index > inorder_end_index) return null;

        int val = preorder[preorder_start_index];
        TreeNode root = new TreeNode(val);
        for (int i = inorder_start_index; i <= inorder_end_index; i++) {
            if (inorder[i] == val) {
                root.left = buildTree(preorder, inorder, preorder_start_index + 1, i - inorder_start_index + preorder_start_index, inorder_start_index, i - 1);
                root.right = buildTree(preorder, inorder, i - inorder_start_index + preorder_start_index + 1, preorder_end_index, i + 1, inorder_end_index);
            }
        }

        return root;
    }

    public static void main(String[] args) {
//        前序遍历 preorder = [3,9,20,15,7]
//        中序遍历 inorder = [9,3,15,20,7]

    }
}
