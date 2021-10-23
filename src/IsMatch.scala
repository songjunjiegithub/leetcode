object IsMatch {
    def isMatch(s: String, p: String): Boolean = {
        val db = Array.ofDim[Int](s.length, p.length)
        find(s, p, 0, 0, db)
    }

    def find(s: String, p: String, index1: Int, index2: Int, db: Array[Array[Int]]): Boolean = {
        if (p.length == index2) {
            return s.length == index1
        }

        if (s.length == index1) {
            return p(index2) == '*' && find(s, p, index1, index2 + 1, db)
        }

        if (db(index1)(index2) != 0) {
            if (db(index1)(index2) == 1) return true
            return false
        }

        if (p(index2) == '?') {
            db(index1)(index2) = if (find(s, p, index1 + 1, index2 + 1, db)) 1 else 2
            return db(index1)(index2) == 1
        }

        if (p(index2) == '*') {
            db(index1)(index2) = if (find(s, p, index1, index2 + 1, db) || find(s, p, index1 + 1, index2, db)) 1 else 2
            return db(index1)(index2) == 1
        }

        db(index1)(index2) = if (s(index1) == p(index2) && find(s, p, index1 + 1, index2 + 1, db)) 1 else 2
        db(index1)(index2) == 1
    }

    def main(args: Array[String]): Unit = {
        // 输入:
//         val s = "aa"
//         val p = "a"
        // 输出: false
        // 解释: "a" 无法匹配 "aa" 整个字符串。
        // 示例 2:

        // 输入:
//         val s = "aa"
//         val p = "*"
        // 输出: true
        // 解释: '*' 可以匹配任意字符串。
        // 示例 3:

        // 输入:
//         val s = "cb"
//         val p = "?a"
        // 输出: false
        // 解释: '?' 可以匹配 'c', 但第二个 'a' 无法匹配 'b'。
        // 示例 4:

        // 输入:
//         val s = "adceb"
//         val p = "*a*b"
        // 输出: true
        // 解释: 第一个 '*' 可以匹配空字符串, 第二个 '*' 可以匹配字符串 "dce".
        // 示例 5:

        // 输入:
//         val s = "acdcb"
//         val p = "a*c?b"
        // 输出: false

//        print(isMatch(s, p))

        val s = "bbbbbbbabbaabbabbbbaaabbabbabaaabbababbbabbbabaaabaab"
        val p = "b*b*ab**ba*b**b***bba"
        isMatch(s, p)
//        println(count)
    }
}
