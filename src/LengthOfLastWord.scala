object LengthOfLastWord {
    def lengthOfLastWord(s: String): Int = {
        var right = s.length - 1
        while (right >= 0 && s(right) == ' ') right -= 1
        if (right == -1) return 0
        var left = right - 1
        while (left >= 0 && s(left) != ' ') left -= 1

        right - left
    }

    def main(args: Array[String]): Unit = {
        println(lengthOfLastWord("Hello World"))
    }
}
