object Permute {
    var result = List.empty[List[Int]]
    def permute(nums: Array[Int]): List[List[Int]] = {
        result = List.empty[List[Int]]

        find(nums, 0)

        return result
    }

    def find(nums: Array[Int], index: Int): Unit = {
        if (index == nums.length)  {
            result = result :+ nums.toList
            return
        }

        for (i <- index until nums.length) {
            swap(nums, index, i)
            find(nums, index + 1)
            swap(nums, index, i)
        }
    }

    def swap(nums: Array[Int], i: Int, j: Int) = {
        var tmp = nums(i)
        nums(i) = nums(j)
        nums(j) = tmp
    }

    def main(args: Array[String]): Unit = {
//        输入: [1,2,3]
//        输出:
//        [
//        [1,2,3],
//        [1,3,2],
//        [2,1,3],
//        [2,3,1],
//        [3,1,2],
//        [3,2,1]
//        ]
        var nums = Array(1,2,3)
        permute(nums).foreach(x => {
            print(x)
            println()
        })
    }
}
