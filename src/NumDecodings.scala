object NumDecodings {
    def numDecodings(s: String): Int = {
        val db = new Array[Int](s.length)
        numDecodings(s, 0, db)
    }

    def numDecodings(s: String, i: Int, db: Array[Int]): Int = {
        if (i == s.length) return 1
        if (s(i) == '0') return 0
        if (db(i) != 0) return if (db(i) == -1) 0 else db(i)

        val result = numDecodings(s: String, i + 1, db) + (if (i + 1 < s.length && (s(i + 1) - '0' + 10 * (s(i) - '0')) <= 26) numDecodings(s: String, i + 2, db) else 0)
        db(i) = if (result == 0) -1 else result

        result
    }

    def main(args: Array[String]): Unit = {
        println(numDecodings("0"))
    }
}
