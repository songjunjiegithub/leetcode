object Merge {
    def merge(intervals: Array[Array[Int]]): Array[Array[Int]] = {
        val arr = intervals.sortBy(x => x(0))
        val result = scala.collection.mutable.ListBuffer.empty[Array[Int]]

        var Array(left, right) = arr(0)
        for (i <- 1 until arr.length) {
            if (arr(i)(0) <= right) {
                right = Math.max(right, arr(i)(1))
            } else {
                result.addOne(Array(left, right))
                left = arr(i)(0)
                right = arr(i)(1)
            }
        }
        result.addOne(Array(left, right))

        result.toArray
    }

    // 88题
    def merge(nums1: Array[Int], m: Int, nums2: Array[Int], n: Int): Unit = {
        var (i, j, index) = (m - 1, n - 1, m + n - 1)

        while (i >= 0 && j >= 0) {
            if (nums1(i) >= nums2(j)) {
                nums1(index) = nums1(i)
                i -= 1
            } else {
                nums1(index) = nums2(j)
                j -= 1
            }

            index -= 1
        }

        if (i < 0 ) for (k <- 0 to j) nums1(k) = nums2(k)
    }

    def main(args: Array[String]): Unit = {
//        输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
//        输出：[1,2,2,3,5,6]
        val nums1 = Array(1,2,3,0,0,0)
        val m = 3
        val nums2 = Array(2,5,6)
        val n = 3
        merge(nums1, m, nums2, n)
        nums1.foreach(println)

//        输入：nums1 = [1], m = 1, nums2 = [], n = 0
//        输出：[1]
    }
}
