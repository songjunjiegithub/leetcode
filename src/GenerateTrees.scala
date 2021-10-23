object GenerateTrees {
    def generateTrees(n: Int): List[TreeNode] = {
        if (n == 0) return List.empty[TreeNode]

        generateTrees(n, 1, n)
    }

    def generateTrees(n: Int, i: Int, j: Int): List[TreeNode] = {
        if (i > j) return List(null)
        val result = scala.collection.mutable.ListBuffer.empty[TreeNode]
        for (index <- i to j) {
            for (left <- generateTrees(n, i, index - 1);
                 right <- generateTrees(n, index + 1, j)) {
                val ele = new TreeNode(index, null, null)
                ele.left = left
                ele.right = right
                result.addOne(ele)
            }
        }

        result.toList
    }
}
