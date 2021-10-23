object IsScramble {
    def isScramble(s1: String, s2: String): Boolean = {
//        if (s1.length != s2.length) return false
        if (s1.equals(s2)) return true
        if (s1.length == 1) return false

        val s1_arr = Array.ofDim[Int](s1.length, 26)
        val s2_arr = Array.ofDim[Int](s2.length, 26)
        val db = Array.ofDim[Int](s1.length, s2.length, s1.length)

        for (j <- 0 until s1.length) {
            for (i <- j until s1.length) {
                s1_arr(i)(s1(j) - 'a') += 1
            }
        }

        for (j <- 0 until s2.length) {
            for (i <- j until s2.length) {
                s2_arr(i)(s2(j) - 'a') += 1
            }
        }

        if (!isEqual(s1_arr, s2_arr, -1, s1.length - 1, -1, s2.length - 1)) return false

        dfs(s1, s2, 0, 0, s1.length, s1_arr, s2_arr, db)
    }

    def isEqual(s1_arr: Array[Array[Int]], s2_arr: Array[Array[Int]], i1: Int, i2: Int, j1: Int, j2: Int): Boolean = {
        for (index <- 0 until 26) {
            if (s1_arr(i2)(index) - (if(i1 < 0) 0 else s1_arr(i1)(index)) != s2_arr(j2)(index) - (if(j1 < 0) 0 else s2_arr(j1)(index))) return false
        }

        true
    }

    def dfs(s1: String, s2: String, i: Int, j: Int, len: Int, s1_arr: Array[Array[Int]], s2_arr: Array[Array[Int]], db: Array[Array[Array[Int]]]): Boolean = {
        if (db(i)(j)(len - 1) != 0) return db(i)(j)(len - 1) == 1

        if (len == 1) return s1(i) == s2(j)

        for (index <- i until i + len - 1 if isEqual(s1_arr, s2_arr, i - 1, index, j - 1, j + index - i)) {
            if (dfs(s1, s2, i, j, index - i + 1, s1_arr, s2_arr, db) && dfs(s1, s2, index + 1, j + index - i + 1, len - (index - i + 1), s1_arr, s2_arr, db)) {
                db(i)(j)(len - 1) = 1
                return true
            }
        }

        for (index <- i until i + len - 1 if isEqual(s1_arr, s2_arr, i - 1, index, j + len - 1 + i - index - 1, j + len - 1)) {
            if (dfs(s1, s2, i, j + len - 1 + i - index, index - i + 1, s1_arr, s2_arr, db) && dfs(s1, s2, index + 1, j, len - (index - i + 1), s1_arr, s2_arr, db)) {
                db(i)(j)(len - 1) = 1
                return true
            }
        }

        db(i)(j)(len - 1) = -1
        false
    }

    def main(args: Array[String]): Unit = {
//        输入：s1 = "abcde", s2 = "caebd"
//        输出：false
//        println(isScramble("abcde", "caebd"))

//        输入：s1 = "a", s2 = "a"
//        输出：true

//        "great"
//        "rgeat"
        println(isScramble("great", "rgeat"))
    }
}
