object SpiralOrder {
    def spiralOrder(matrix: Array[Array[Int]]): List[Int] = {
        var result = List.empty[Int]
        var (left, right, top, bottom) = (0, matrix(0).length - 1, 0, matrix.length - 1)

        while (left <= right && top <= bottom) {
            for (i <- left to right) result = result :+ matrix(top)(i)

            for (j <- top + 1 to bottom) result = result :+ matrix(j)(right)

            if (left < right && top < bottom) {

                for (i <- Range(right - 1, left - 1, -1)) result = result :+ matrix(bottom)(i)

                for (j <- Range(bottom - 1, top, -1)) result = result :+ matrix(j)(left)
            }

            left += 1
            right -= 1
            top += 1
            bottom -= 1
        }

        result
    }

    def main(args: Array[String]): Unit = {
        var matrix = Array(Array(1,2,3,4),Array(5,6,7,8),Array(9,10,11,12))
        spiralOrder(matrix).foreach(println)
    }
}
