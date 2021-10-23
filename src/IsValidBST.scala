object IsValidBST {
//    var value = Int.MinValue.toLong - 1
//
//    def isValidBST(root: TreeNode): Boolean = {
//        this.value = Int.MinValue.toLong - 1
//
//        dfs(root)
//    }
//
//    def dfs(root: TreeNode): Boolean = {
//        if (root == null) return true
//
//        if (!dfs(root.left)) return false
//        if (root.value.toLong <= this.value) return false
//        this.value = root.value.toLong
//        if (!dfs(root.right)) return false
//
//        true
//    }

    // 方法2
    def isValidBST(root: TreeNode): Boolean = {
        var ele = root
        var inorder = Int.MinValue.toLong - 1
        val stack = scala.collection.mutable.Stack.empty[TreeNode]
        while (ele != null || !stack.isEmpty) {
            if (ele != null) {
                stack.push(ele)
                ele = ele.left
            } else {
                ele = stack.pop
                if (ele.value.toLong <= inorder) return false
                inorder = ele.value.toLong
                ele = ele.right
            }
        }

        true
    }
}
