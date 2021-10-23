object GenerateMatrix {
    def generateMatrix(n: Int): Array[Array[Int]] = {
        val result = Array.ofDim[Int](n, n)
        var (left, right, top, bottom) = (0, n - 1, 0, n - 1)
        var last = 1
        while (left <= right && top <= bottom) {
            for (i <- left to right) result(top)(i) = i - left + last
            for (j <- top + 1 to bottom) result(j)(right) = j - top + last + right - left
            if (left < right && top < bottom) {
                for (i <- Range(right - 1, left - 1, -1)) result(bottom)(i) = right - i + last + right - left + bottom - top
                for (j <- Range(bottom - 1, top, -1)) result(j)(left) = bottom - j + last + right - left + bottom - top + right - left
            }

            last = last + 2 * (right - left + bottom - top)
            left += 1
            right -= 1
            top += 1
            bottom -= 1
        }

        result
    }

    def main(args: Array[String]): Unit = {
        generateMatrix(5).foreach(x => {
            x.foreach(y => print(y + ","))
            println()
        })
    }
}
