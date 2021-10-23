object MaximalRectangle {
    def maximalRectangle(matrix: Array[Array[Char]]): Int = {
        if (matrix.length == 0 || matrix(0).length == 0) return 0
        val arr = new Array[Int](matrix(0).length)

        val left = new Array[Int](matrix(0).length)
        val right = new Array[Int](matrix(0).length)

        var result = 0
        for (j <- 0 until matrix.length) {
            result = Math.max(largestRectangleArea(arr, matrix, j, left, right), result)
        }

        result
    }

    def largestRectangleArea(heights: Array[Int], matrix: Array[Array[Char]], j: Int, left: Array[Int], right: Array[Int]): Int = {
        if (heights.length == 0) return 0
        if (heights.length == 1) {
            if (matrix(j)(0) == '1') {
                heights(0) += 1
            } else {
                heights(0) = 0
            }
            return heights(0)
        }

        val stack = scala.collection.mutable.Stack.empty[Int]
        for (i <- 0 until heights.length) {
            if (matrix(j)(i) == '0') {
                heights(i) = 0
            } else {
                heights(i) += 1
            }

            while (!stack.isEmpty && heights(stack.top) >= heights(i)) stack.pop

            left(i) = if (stack.isEmpty) -1 else stack.top

            stack.push(i)
        }

        stack.clear

        for (i <- Range(heights.length - 1, -1, -1)) {
            while (!stack.isEmpty && heights(stack.top) >= heights(i)) stack.pop

            right(i) = if (stack.isEmpty) heights.length else stack.top

            stack.push(i)
        }

        var result = 0

        for (i <- 0 until heights.length) result = Math.max(result, heights(i) * (right(i) - left(i) - 1))

        result
    }

    def main(args: Array[String]): Unit = {
//        输入：matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
//        输出：6
//        解释：最大矩形如上图所示。
//        val matrix = Array(Array('1','0','1','0','0'), Array('1','0','1','1','1'), Array('1','1','1','1','1'), Array('1','0','0','1','0'))
//        println(maximalRectangle(matrix))

//        输入：matrix = []
//        输出：0

//        val matrix = Array(Array.empty[Char])
//        println(maximalRectangle(matrix))

//        输入：matrix = [["0"]]
//        输出：0

//        输入：matrix = [["1"]]
//        输出：1
        val matrix = Array(Array('1'))
        println(maximalRectangle(matrix))


//        输入：matrix = [["0","0"]]
//        输出：0
    }
}
