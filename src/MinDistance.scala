object MinDistance {
    def minDistance(word1: String, word2: String): Int = {
        if (word1.length == 0 || word2.length == 0) return word1.length + word2.length

        val db = new Array[Int](word1.length + 1)
        for (j <- 0 to word1.length) db(j) = j

        var previous = 0
        for (i <- 1 to word2.length) {
            db(0) = i
            previous = i - 1

            for (j <- 1 to word1.length) {
                if (word1(j - 1) == word2(i - 1)) {
                    val tmp = db(j)
                    db(j) = previous
                    previous = tmp
                } else {
                    val tmp = db(j)
                    db(j) = 1 + Math.min(Math.min(previous, db(j - 1)), db(j))
                    previous = tmp
                }
            }
        }

        db(word1.length)
    }

    def main(args: Array[String]): Unit = {
//        输入：word1 = "horse", word2 = "ros"
//        输出：3
//        解释：
//        horse -> rorse (将 'h' 替换为 'r')
//        rorse -> rose (删除 'r')
//        rose -> ros (删除 'e')

//        val word1 = "horse"
//        val word2 = "ros"
//        println(minDistance(word1, word2))

//        输入：word1 = "intention", word2 = "execution"
//        输出：5
//        解释：
//        intention -> inention (删除 't')
//        inention -> enention (将 'i' 替换为 'e')
//        enention -> exention (将 'n' 替换为 'x')
//        exention -> exection (将 'n' 替换为 'c')
//        exection -> execution (插入 'u')
//        val word1 = "intention"
//        val word2 = "execution"
//        println(minDistance(word1, word2))
        val word1 = "park"
        val word2 = "spake"
        println(minDistance(word1, word2))
    }
}
