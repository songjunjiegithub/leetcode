object IsSameTree {
    def isSameTree(p: TreeNode, q: TreeNode): Boolean = {
        if (p == null || q == null) return p == q

        if (p.value != q.value) return false

        isSameTree(p.left, q.left) && isSameTree(p.right, q.right)
    }
}
