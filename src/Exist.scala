object Exist {
    def exist(board: Array[Array[Char]], word: String): Boolean = {
        if (word.length > board.length * board(0).length) return false

        val arr = Array.ofDim[Int](board.length, board(0).length)

        for {j <- 0 until board.length;
             i <- 0 until board(0).length if dfs(board, word, i, j, arr)} {
            return true
        }

        false
    }

    def dfs(board: Array[Array[Char]], word: String, i: Int, j: Int, arr: Array[Array[Int]]): Boolean = {
        if (word == "") return true

        if (i >= board(0).length || j >= board.length || i < 0 || j < 0 || arr(j)(i) != 0) return false

        arr(j)(i) = 1
        val result = board(j)(i) == word(0) && (dfs(board, word.slice(1, word.length), i - 1, j, arr) || dfs(board, word.slice(1, word.length), i + 1, j, arr) || dfs(board, word.slice(1, word.length), i, j - 1, arr) || dfs(board, word.slice(1, word.length), i, j + 1, arr))
        arr(j)(i) = 0

        result
    }

    def main(args: Array[String]): Unit = {
//        输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
//        输出：true

//        val board = """[["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]]""".replace("[[", "").replace("]]", "").replace("\"", "").split("\\]\\,\\[").map(x => x.split(",").map(_(0)))
//        val word = "ABCCED"
//        println(exist(board, word))

//        输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
//        输出：true
//        val board = """[["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]]""".replace("[[", "").replace("]]", "").replace("\"", "").split("\\]\\,\\[").map(x => x.split(",").map(_(0)))
//        val word = "SEE"
//        println(exist(board, word))

//        输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
//        输出：false
        val board = """[["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]]""".replace("[[", "").replace("]]", "").replace("\"", "").split("\\]\\,\\[").map(x => x.split(",").map(_(0)))
        val word = "ABCB"
        println(exist(board, word))
    }
}
