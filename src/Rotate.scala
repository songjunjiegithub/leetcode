object Rotate {
    def rotate(matrix: Array[Array[Int]]): Unit = {
        var tmp = 0
        val n = matrix.length
        for {
            i <- 0 to matrix.length / 2 - 1;
            j <- 0 to (matrix.length - 1) / 2
        } {
            tmp = matrix(i)(j)
            matrix(i)(j) = matrix(n - j - 1)(i)
            matrix(n - j - 1)(i) = matrix(n - i - 1)(n - j - 1)
            matrix(n - i - 1)(n - j - 1) = matrix(j)(n - i - 1)
            matrix(j)(n - i - 1) = tmp
        }
    }

    def main(args: Array[String]): Unit = {
//        输入：matrix = [[1,2,3],
//                       [4,5,6],
//                       [7,8,9]]
//        输出：[[7,4,1],[8,5,2],[9,6,3]]
//        val matrix = Array(Array(1,2,3),Array(4,5,6),Array(7,8,9))
//        rotate(matrix)
//        matrix.foreach(x => println(x.toList))


//        输入：matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
//        输出：[[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]
//        val matrix = Array(Array(5,1,9,11),Array(2,4,8,10),Array(13,3,6,7),Array(15,14,12,16))
//        rotate(matrix)
//        matrix.foreach(x => println(x.toList))

//        输入：matrix = [[1]]
//        输出：[[1]]
//        val matrix = Array(Array(1))
//        rotate(matrix)
//        matrix.foreach(x => println(x.toList))

//        输入：matrix = [[1,2],[3,4]]
//        输出：[[3,1],[4,2]]
        val matrix = Array(Array(1,2), Array(3, 4))
        rotate(matrix)
        matrix.foreach(x => println(x.toList))
    }
}
