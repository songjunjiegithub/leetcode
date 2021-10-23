object PlusOne {
    def plusOne(digits: Array[Int]): Array[Int] = {
        var carry = 1
        for (i <- 0 until digits.length) {
            val tmp = digits(digits.length - 1 - i) + carry
            carry = tmp / 10
            digits(digits.length - 1 - i) = tmp % 10
        }

        if (carry == 0) return digits

        val arr = new Array[Int](digits.length + 1)
        arr(0) = 1
        for (i <- 0 until digits.length) arr(i + 1) = digits(i)

        arr
    }

    def main(args: Array[String]): Unit = {
//        输入：digits = [1,2,3]
//        输出：[1,2,4]
//        解释：输入数组表示数字 123。
        val digits = Array(1,2,3)



    }
}
