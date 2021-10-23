object FullJustify {
    def fullJustify(words: Array[String], maxWidth: Int): List[String] = {
        val result = scala.collection.mutable.ListBuffer.empty[String]
        var i = 0
        var j = 0
        var surplus = maxWidth
        while (j < words.length) {
            if (words(j).length <= surplus) {
                surplus -= words(j).length + 1
                j += 1
            } else {
                if (j == i + 1) {
                    result.addOne(words(i) + java.nio.CharBuffer.allocate(maxWidth - words(i).length).toString.replace(0.toChar, ' '))
                } else {
                    val space = (surplus + j - i) / (j - i - 1)
                    val mod = (surplus + j - i) % (j - i - 1)
                    var str = ""
                    for (index <- i until j - 1) {
                        str += words(index) + java.nio.CharBuffer.allocate(space + (if (index - i + 1 <= mod) 1 else 0)).toString.replace(0.toChar, ' ')
                    }
                    result.addOne(str + words(j - 1))
                }

                i = j
                surplus = maxWidth
            }
        }

        result.addOne(words.slice(i, j).mkString(" ") + java.nio.CharBuffer.allocate(surplus + 1).toString.replace(0.toChar, ' '))

        result.toList
    }

    def main(args: Array[String]): Unit = {
        //        输入:
        //            words = ["This", "is", "an", "example", "of", "text", "justification."]
        //        maxWidth = 16
        //        输出:
        //        [
        //           "This    is    an",
        //           "example  of text",
        //           "justification.  "
        //        ]
        //        java.nio.CharBuffer.allocate(3).toString.replace('\0', ' ')

//        val words = Array("This", "is", "an", "example", "of", "text", "justification.")
//        val maxWidth = 16
//        println(fullJustify(words, maxWidth))

        //        输入:
        //            words = ["What","must","be","acknowledgment","shall","be"]
        //        maxWidth = 16
        //        输出:
        //        [
        //          "What   must   be",
        //          "acknowledgment  ",
        //          "shall be        "
        //        ]
        //        解释: 注意最后一行的格式应为 "shall be    " 而不是 "shall     be",
        //             因为最后一行应为左对齐，而不是左右两端对齐。
        //        第二行同样为左对齐，这是因为这行只包含一个单词。

//        val words = Array("What","must","be","acknowledgment","shall","be")
//        val maxWidth = 16
//        println(fullJustify(words, maxWidth))

        //        输入:
        //            words = ["Science","is","what","we","understand","well","enough","to","explain",
        //                 "to","a","computer.","Art","is","everything","else","we","do"]
        //        maxWidth = 20
        //        输出:
        //        [
        //          "Science  is  what we",
        //        "understand      well",
        //          "enough to explain to",
        //          "a  computer.  Art is",
        //          "everything  else  we",
        //          "do                  "
        //        ]

        val words = Array("Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do")
        val maxWidth = 20
        fullJustify(words, maxWidth).foreach(println)

    }
}
