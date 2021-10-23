object SortColors {
    def sortColors(nums: Array[Int]): Unit = {
        var (i, k, j) = (0, 0, nums.length - 1)
        while (k <= j) {
            if (nums(k) == 0) {
                swap(nums, i, k)
                i += 1
                k += 1
            } else if (nums(k) == 2) {
                swap(nums, k, j)
                j -= 1
            } else {
                k += 1
            }
        }
    }

    def swap(nums: Array[Int], i: Int, j: Int): Unit = {
        val tmp = nums(i)
        nums(i) = nums(j)
        nums(j) = tmp
    }
}
