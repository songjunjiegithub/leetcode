object Subsets {
    def subsets(nums: Array[Int]): List[List[Int]] = {
        val result = scala.collection.mutable.ListBuffer.empty[List[Int]]
        result.addOne(List.empty[Int])
        for (ele <- nums) {
            for (i <- 0 until result.length) {
                result.addOne(result(i) :+ ele)
            }
        }

        result.toList
    }
}
