object MaxSubArray {
    def maxSubArray(nums: Array[Int]): Int = {
//        val arr = new Array[Int](nums.length)
//        var result = -100000
//        var i = -1
//        nums.foldLeft(0)((x, y) => {
//            i += 1
//            arr(i) = x + y
//            result = Math.max(result, arr(i))
//            arr(i)
//        })
//
//
//        for {
//            i <- 1 until nums.length;
//            j <- i until nums.length
//             } result = Math.max(result, arr(j) - arr(i - 1))
//
//        result

        var result = nums(0)
        var prev = nums(0)
        nums.foreach(x => {
            prev = Math.max(prev + x, x)
            result = Math.max(result, prev)
        })

        result
    }

    def main(args: Array[String]): Unit = {
//        输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
//        输出：6
//        解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
//        val nums = Array(-2,1,-3,4,-1,2,1,-5,4)
//        println(maxSubArray(nums))

//        输入：nums = [1]
//        输出：1
//        val nums = Array(1)
//        println(maxSubArray(nums))

//        输入：nums = [0]
//        输出：0
//        val nums = Array(0)
//        println(maxSubArray(nums))

//        输入：nums = [-1]
//        输出：-1
//        val nums = Array(-1)
//        println(maxSubArray(nums))

//        输入：nums = [-100000]
//        输出：-100000
//        val nums = Array(-100000)
//        println(maxSubArray(nums))
    }
}
