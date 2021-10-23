object Trap {
    def trap(height: Array[Int]): Int = {
        if (height.isEmpty) return 0
        var result = 0
        var (i, j) = (0, height.length - 1)
        while (i < j) {
            if (height(i) <= height(j)) {
                var k = i + 1
                while (k < j && height(k) <= height(i)) {
                    result += height(i) - height(k)
                    k += 1
                }
                i = k
            } else {
                var k = j - 1
                while (k > i && height(k) <= height(j)) {
                    result += height(j) - height(k)
                    k -= 1
                }
                j = k
            }
        }

        result
    }

    def main(args: Array[String]): Unit = {
        //        输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
        //        输出：6
        //        解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。
        //
        //        输入：height = [4,2,0,3,2,5]
        //        输出：9
//        val height = Array(0,1,0,2,1,0,1,3,2,1,2,1)
        val height = Array(4,2,0,3,2,5)
        println(trap(height))
    }
}
