object NumTrees {
    def numTrees(n: Int): Int = {
        if (n == 0) return 0

        val db = new Array[Int](n + 1)

        db(0) = 1

        for (i <- 1 to n) {
//            db(i) = db(0) * db(n - 1) + db(1) * db(n - 2) + ... + db(n - 1) * db(0)
            for (j <- 0 until i) db(i) += db(j) * db(i - 1 - j)
        }

        db(n)
    }

    def main(args: Array[String]): Unit = {
        println(numTrees(3))
    }
}
