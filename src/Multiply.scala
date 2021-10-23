object Multiply {
    def multiply(num1: String, num2: String): String = {
        if (num1 == "0" || num1 == "" || num2 == "0" || num2 == "") return "0"

        val arr = new Array[Int](num1.length + num2.length)
        var decimal = 0
        var tmp = 0
        for {
            i <- Range(num1.length - 1, -1, -1);
            j <- Range(num2.length - 1, -1, -1)
        } {
            tmp = arr(i + j + 1) + (num1(i) - '0') * (num2(j) - '0')
            arr(i + j + 1) = tmp % 10
            decimal = tmp / 10
            arr(i + j) = arr(i + j) + decimal
        }

        if (arr(0) != 0) return arr.foldLeft("")((x, y) => x + y.toString) else return arr.foldLeft("")((x, y) => x + y.toString).slice(1, arr.length)
    }

    def main(args: Array[String]): Unit = {
//        输入: num1 = "2", num2 = "3"
//        输出: "6"
//
//        输入: num1 = "123", num2 = "456"
//        输出: "56088"

        val num1 = "123"
        val num2 = "456"

        println(multiply(num1, num2))
    }
}
