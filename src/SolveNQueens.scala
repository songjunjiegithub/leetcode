object SolveNQueens {
    import scala.collection.mutable.ListBuffer
    var (column, left, right) = (0, 0, 0)

    def solveNQueens(n: Int): List[List[String]] = {
        column = 0
        left = 0
        right = 0
        val result = ListBuffer.empty[List[String]]
        val str = (0 until n).foldLeft("")((x, y) => x + ".")
        val road = new Array[Int](n)

        find(n, 0, result, road, str)

        result.toList
    }

    def find(n: Int, index: Int, result: ListBuffer[List[String]], road: Array[Int], str: String): Unit = {
        if (index == n) result.addOne(road.foldLeft(List.empty[String])((x, y) => x :+ (str.slice(0, y) + "Q" + str.slice(y + 1, n))))

        for (j <- 0 until n if (column & 1 << j) == 0 && (right & 1 << (index + j)) == 0 && (left & 1 << (n - 1 + index - j)) == 0) {
            road(index) = j
            column ^= 1 << j
            right ^= 1 << (index + j)
            left ^= 1 << (n - 1 + index - j)
            find(n, index + 1, result, road, str)
            road(index) = 0
            column ^= 1 << j
            right ^= 1 << (index + j)
            left ^= 1 << (n - 1 + index - j)
        }
    }

    def main(args: Array[String]): Unit = {
//        输入：n = 4
//        输出：[[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
//        解释：如上图所示，4 皇后问题存在两个不同的解法。
        println(solveNQueens(4))

//        输入：n = 1
//        输出：[["Q"]]
    }
}
