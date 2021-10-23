object RemoveDuplicates {
    def removeDuplicates(nums: Array[Int]): Int = {
        var (i, j) = (1, 1)

        while (j < nums.length) {
            if (i == 1 || nums(j) != nums(i - 2)) {
                swap(nums, i, j)
                i += 1
            }

            j += 1
        }

        nums.foreach(print)
        i
    }

    def swap(nums: Array[Int], i: Int, j: Int): Unit = {
        val tmp = nums(i)
        nums(i) = nums(j)
        nums(j) = tmp
    }

    def main(args: Array[String]): Unit = {
//        输入：nums = [1,1,1,2,2,3]
//        输出：5, nums = [1,1,2,2,3]
//        解释：函数应返回新长度 length = 5, 并且原数组的前五个元素被修改为 1, 1, 2, 2, 3 。 不需要考虑数组中超出新长度后面的元素。
        val nums = Array(1,1,1,2,2,3)
        println(removeDuplicates(nums))
    }
}
