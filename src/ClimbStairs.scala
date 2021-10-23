object ClimbStairs {
    def climbStairs(n: Int): Int = {
        if (n == 1) return 1
        if (n == 2) return 2

        var (a, b) = (2, 1)
        var tmp = 0
        for (_ <- 0 until n - 2) {
            tmp = a + b
            b = a
            a = tmp
        }

        a
    }
}
