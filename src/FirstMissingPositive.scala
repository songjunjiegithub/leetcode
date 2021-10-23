object FirstMissingPositive {
    def firstMissingPositive(nums: Array[Int]): Int = {
        val len = nums.length
        for (i <- 0 until len if nums(i) <= 0) nums(i) = len + 1

        for (i <- 0 until len if Math.abs(nums(i)) <= len && nums(Math.abs(nums(i)) - 1) > 0) {
            nums(Math.abs(nums(i)) - 1) = - nums(Math.abs(nums(i)) - 1)
        }

        for (i <- 0 until len if nums(i) > 0) return i + 1

        len + 1
    }

    def main(args: Array[String]): Unit = {
        val nums = Array(1,2,0)
        println(firstMissingPositive(nums))
    }
}