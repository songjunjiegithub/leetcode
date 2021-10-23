object LargestRectangleArea {
    def largestRectangleArea(heights: Array[Int]): Int = {
        if (heights.length == 0) return 0
        if (heights.length == 1) return heights(0)
        val left = new Array[Int](heights.length)
        val right = new Array[Int](heights.length)

        val stack = scala.collection.mutable.Stack.empty[Int]
        for (i <- 0 until heights.length) {
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
//        输入: [2,1,5,6,2,3]
//        输出: 10
        val heights = Array(1)
        println(largestRectangleArea(heights))
    }
}
