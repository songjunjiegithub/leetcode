object RestoreIpAddresses {
    def restoreIpAddresses(s: String): List[String] = {
        restoreIpAddresses(s, 0, 4)
    }

    def restoreIpAddresses(s: String, i: Int, count: Int): List[String] = {
        if (s.length <= i) return List.empty[String]

        if (count == 1) {
            if (s(i) == '0') {
                if (s.length - i == 1) return List("0")
                return List.empty[String]
            }
            if (s.length - i == 1 || s.length - i == 2 || (s.length - i == 3 && s.slice(i, s.length) <= "255")) return List(s.slice(i, s.length))
            return List.empty[String]
        }

        val result = scala.collection.mutable.ListBuffer.empty[String]

        restoreIpAddresses(s, i + 1, count - 1).foreach(x => result.addOne(s(i).toString + "." + x))
        if (s(i) != '0') {
            restoreIpAddresses(s, i + 2, count - 1).foreach(x => result.addOne(s.slice(i, i + 2) + "." + x))
            if (s.slice(i, i + 3) <= "255") restoreIpAddresses(s, i + 3, count - 1).foreach(x => result.addOne(s.slice(i, i + 3) + "." + x))
        }
        result.toList
    }

    def main(args: Array[String]): Unit = {
        //        输入：s = "25525511135"
        //        输出：["255.255.11.135","255.255.111.35"]
        println(restoreIpAddresses("25525511135"))
    }
}
