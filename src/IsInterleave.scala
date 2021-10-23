object IsInterleave {
    def isInterleave(s1: String, s2: String, s3: String): Boolean = {
        val db = Array.ofDim[Int](s1.length, s2.length)
        isInterleave(s1, s2, s3, 0, 0, 0, db)
    }

    def isInterleave(s1: String, s2: String, s3: String, i: Int, j: Int, k: Int, db: Array[Array[Int]]): Boolean = {
        if (i == s1.length || j == s2.length || k == s3.length) return s1.slice(i, s1.length) + s2.slice(j, s2.length) == s3.slice(k, s3.length)

        if (db(i)(j) != 0) return db(i)(j) == 1

        val result = (s1(i) == s3(k) && isInterleave(s1, s2, s3, i + 1, j, k + 1, db)) || (s2(j) == s3(k) && isInterleave(s1, s2, s3, i, j + 1, k + 1, db))

        db(i)(j) == (if (result) 1 else -1)

        result
    }

    def main(args: Array[String]): Unit = {
//        输入：s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
//        输出：true
//        val s1 = "aabcc"
//        val s2 = "dbbca"
//        val s3 = "aadbbcbcac"
//        println(isInterleave(s1, s2, s3))

//        输入：s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
//        输出：false
//        val s1 = "aabcc"
//        val s2 = "dbbca"
//        val s3 = "aadbbbaccc"
//        println(isInterleave(s1, s2, s3))

//        输入：s1 = "", s2 = "", s3 = ""
//        输出：true
        println(isInterleave("", "", ""))
    }
}
