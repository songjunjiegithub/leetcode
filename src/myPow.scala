object myPow {
    def myPow(x: Double, n: Int): Double = {
        if (x == 1) return 1
        if (n < 0) return 1.0 / x / myPow(x, -(n + 1))

        var result = 1.0
        var i = n
        var tmp = x
        while (i != 0) {
            if (i % 2 == 1) {
                result *= tmp
            }
            tmp = tmp * tmp
            i = i >> 1
        }

        result
    }

    def main(args: Array[String]): Unit = {
//        输入：x = 2.00000, n = 10
//        输出：1024.00000
//        val x = 2.00000
//        val n = 10
//        print(myPow(x, n))


//        输入：x = 2.10000, n = 3
//        输出：9.26100
//        val x = 2.10000
//        val n = 3
//        print(myPow(x, n))

//        输入：x = 2.00000, n = -2
//        输出：0.25000
//        解释：2-2 = 1/22 = 1/4 = 0.25
//        val x = 2.000000
//        val n = -2
//        print(myPow(x, n))

        val x = 0.00001
        val n = 2147483647
        print(myPow(x, n))

//        -100.0 < x < 100.0
//        -231 <= n <= 231-1
//        -104 <= xn <= 104
    }
}
