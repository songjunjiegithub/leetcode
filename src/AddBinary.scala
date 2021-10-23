object AddBinary {
    def addBinary(a: String, b: String): String = {
        if (a == "0") return b
        if (b == "0") return a
        if (a.length < b.length) return addBinary(b, a)

        var carry = 0
        var tmp = 0
        var result = ""

        for (i <- 0 until b.length) {
            tmp = b(b.length - 1 - i) - '0' + a(a.length - 1 - i) - '0' + carry
            carry = tmp / 2
            result = tmp % 2 + result
        }
        for (i <- Range(a.length - b.length - 1, -1, -1)) {
            tmp = a(i) - '0' + carry
            carry = tmp / 2
            result = tmp % 2 + result
        }

        if (carry == 0) result else 1 + result
    }
}
