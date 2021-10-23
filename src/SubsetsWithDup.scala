object SubsetsWithDup {
    def subsetsWithDup(nums: Array[Int]): List[List[Int]] = {
        val map = nums.foldLeft(scala.collection.mutable.HashMap.empty[Int, Int])((x, y) => {
            x.put(y, x.getOrElse(y, 0) + 1)
            x
        })

        val result = scala.collection.mutable.ListBuffer.empty[List[Int]]
        result.addOne(List.empty[Int])

        map.foreach{
            case (k, v) => {
                var tmp_list: List[List[Int]] = List.empty[List[Int]]
                for (ele <- result) {
                    var tmp = ele
                    for (_ <- 0 until v) {
                        tmp = tmp :+ k
                        tmp_list = tmp_list :+ tmp
                    }
                }
                result.addAll(tmp_list)
            }
        }

        result.toList
    }

    def main(args: Array[String]): Unit = {
//        输入：nums = [1,2,2]
//        输出：[[],[1],[1,2],[1,2,2],[2],[2,2]]
        val nums = Array(1, 2, 2)
        println(subsetsWithDup(nums))
    }
}
