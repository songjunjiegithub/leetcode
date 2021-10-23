object InorderTraversal {
    def inorderTraversal(root: TreeNode): List[Int] = {
        //        if (root == null) return List.empty[Int]
        //
        //        (inorderTraversal(root.left) :+ root.value) ::: inorderTraversal(root.right)

        // 方法二
        val result = scala.collection.mutable.ListBuffer.empty[Int]
        val stack = scala.collection.mutable.Stack.empty[TreeNode]

        var ele = root

        while (!stack.isEmpty || ele != null) {
            while (ele != null) {
                stack.push(ele)
                ele = ele.left
            }

            ele = stack.pop
            result.addOne(ele.value)
            ele = ele.right
        }

        result.toList
    }


}
