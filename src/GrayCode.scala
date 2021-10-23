object GrayCode {
    def grayCode(n: Int): List[Int] = {
        if (n == 0) return List(0)

        val list = grayCode(n - 1)
        val listBuffer = scala.collection.mutable.ListBuffer.empty[Int]
        for (i <- Range(list.length - 1, -1, -1)) {
            listBuffer.addOne(list(i) + (1 << (n - 1)))
        }

        list ::: listBuffer.toList
    }

    def main(args: Array[String]): Unit = {
        println(grayCode(2))
    }
}
