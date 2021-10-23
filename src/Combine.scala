object Combine {
    import scala.collection.mutable.ListBuffer
    def combine(n: Int, k: Int): List[List[Int]] = {
        if (n < k) return List()
        val result = ListBuffer.empty[List[Int]]
        val road = ListBuffer.empty[Int]

        dfs(k, n, 1, result, road)

        result.toList
    }

    def dfs(k: Int, n: Int, index: Int, result: ListBuffer[List[Int]], road: ListBuffer[Int]): Unit = {
        if (road.length == k) {
            result.addOne(road.toList)
            return
        }

        if (n < index) return

        road.addOne(index)
        dfs(k, n, index + 1, result, road)
        road.remove(road.length - 1)

        dfs(k, n, index + 1, result, road)
    }

    def main(args: Array[String]): Unit = {
        println(combine(4, 2))
    }
}
