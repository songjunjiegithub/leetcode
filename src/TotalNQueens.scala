object TotalNQueens {
//    import scala.collection.mutable.ListBuffer
    var (column, left, right) = (0, 0, 0)
    var result = 0

    def totalNQueens(n: Int): Int = {
        column = 0
        left = 0
        right = 0

        result = 0

        find(n, 0)

        result
    }

    def find(n: Int, index: Int): Unit = {
        if (index == n) result += 1

        for (j <- 0 until n if (column & 1 << j) == 0 && (right & 1 << (index + j)) == 0 && (left & 1 << (n - 1 + index - j)) == 0) {
            column ^= 1 << j
            right ^= 1 << (index + j)
            left ^= 1 << (n - 1 + index - j)
            find(n, index + 1)
            column ^= 1 << j
            right ^= 1 << (index + j)
            left ^= 1 << (n - 1 + index - j)
        }
    }

    def main(args: Array[String]): Unit = {
        for (i <- 1 to 9) println(i, totalNQueens(i))
    }

}
