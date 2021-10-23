object MySqrt {
    def mySqrt(x: Int): Int = {
        if (x == 0 || x == 1) return x
        var (i, j) = (1, Math.min(x, 46341))

        while (i < j) {
            val tmp = i / 2 + j / 2
            if (tmp * tmp <= x) {
                i = tmp + 1
            } else {
                j = tmp
            }
        }

        i - 1
    }

    def main(args: Array[String]): Unit = {
        println(mySqrt(Int.MaxValue))
    }
}
