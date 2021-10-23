object SetZeroes {
    def setZeroes(matrix: Array[Array[Int]]): Unit = {
        var (x, y) = (-1, -1)
        for {i <- 0 until matrix(0).length;
             j <- 0 until matrix.length} {
            if (matrix(j)(i) == 0) {
                if (x == -1) {
                    x = i
                    y = j
                } else {
                    matrix(j)(x) = 0
                    matrix(y)(i) = 0
                }
            }
        }

        if (x == -1) return

        var isZero = true
        for (j <- 0 until matrix.length if j != y) {
            if (matrix(j)(x) != 0) {
                isZero = false
            } else {
                for (i <- 0 until matrix(0).length) matrix(j)(i) = 0
            }
        }

        if (!isZero) {
            for (i <- 0 until matrix(0).length if i != x && matrix(y)(i) == 0) {
                for (j <- 0 until matrix.length) matrix(j)(i) = 0
            }
        }

        for (i <- 0 until matrix(0).length) matrix(y)(i) = 0
        for (j <- 0 until matrix.length) matrix(j)(x) = 0
    }
}
