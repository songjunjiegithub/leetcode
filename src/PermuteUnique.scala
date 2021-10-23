object PermuteUnique {
    import scala.collection.mutable.{HashMap, ListBuffer}
    def permuteUnique(nums: Array[Int]): List[List[Int]] = {
        if (nums.isEmpty) return List()

        val result = ListBuffer.empty[List[Int]]
        val road = ListBuffer.empty[Int]
        val map = HashMap.empty[Int, Int]
        var(i, j) = (-1, 0)
        while (j < nums.length) {
            if (map.get(nums(j)) == None) {
                map.put(nums(j), 1)
                i += 1
                nums(i) = nums(j)
            } else {
                map.put(nums(j), map.get(nums(j)).get + 1)
            }

            j += 1
        }

        find(nums, i, map, result, road)

        result.toList
    }

    def find(nums: Array[Int], i: Int, map: HashMap[Int, Int], result: ListBuffer[List[Int]], road: ListBuffer[Int]): Unit = {
        if (i == -1) {
            result.addOne(road.toList)
            return
        }

        for (j <- 0 to i) {
            val value = map.get(nums(j)).get
            map.put(nums(j), value - 1)
            road.addOne(nums(j))
            if (value == 1) {
                swap(nums, j, i)
                find(nums, i - 1, map, result, road)
                swap(nums, j, i)
            } else {
                find(nums, i, map, result, road)
            }
            map.put(nums(j), value)
            road.remove(road.length - 1)
        }
    }

    def swap(nums: Array[Int], i: Int, j: Int) = {
        val tmp = nums(i)
        nums(i) = nums(j)
        nums(j) = tmp
    }

    def main(args: Array[String]): Unit = {
//        输入：nums = [1,1,2]
//        输出：
//        [[1,1,2],
//        [1,2,1],
//        [2,1,1]]

//        val nums = Array(1,1,2)
//        permuteUnique(nums).foreach(println)


//        输入：nums = [1,2,3]
//        输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
        val nums = Array(1,2,3)
        permuteUnique(nums).foreach(println)
    }
}
