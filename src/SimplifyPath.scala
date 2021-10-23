object SimplifyPath {
    import scala.collection.mutable.Stack

    def simplifyPath(path: String): String = {
        val result = Stack.empty[String]
        var (i, j) = (1, 1)
        while (j < path.length) {
            if (path(j) == '/') {
                operation(path, i, j, result)
                i = j + 1
            }

            j += 1
        }

        operation(path, i, j, result)

        if (result.isEmpty) "/" else result.foldLeft("")((x, y) => "/" + y + x)
    }

    def operation(path: String, i: Int, j: Int, result: Stack[String]): Unit = {
        if (i == j) return
        val str = path.slice(i, j)
        if (str == ".") return
        if (str == "..") {
            if (!result.isEmpty) result.pop()
        } else {
            result.push(str)
        }
    }

    def main(args: Array[String]): Unit = {
//        输入：path = "/home/"
//        输出："/home"
//        解释：注意，最后一个目录名后面没有斜杠。
//        val path = "/home/"
//        println(simplifyPath(path))
//        输入：path = "/../"
//        输出："/"
//        解释：从根目录向上一级是不可行的，因为根目录是你可以到达的最高级。
//        val path = "/../"
//        println(simplifyPath(path))
//
//        输入：path = "/home//foo/"
//        输出："/home/foo"
//        解释：在规范路径中，多个连续斜杠需要用一个斜杠替换。
//        val path = "/home//foo/"
//        println(simplifyPath(path))
//
//        输入：path = "/a/./b/../../c/"
//        输出："/c"
        val path = "/a//b////c/d//././/.."
        println(simplifyPath(path))
    }
}
