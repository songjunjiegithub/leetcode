object CanJump {
    def canJump(nums: Array[Int]): Boolean = {
        var boundary = nums(0)
        var i = 0
        while (boundary < nums.length - 1) {
            if (nums(i) == 0) return false

            var tmp = i
            for (l <- 1 to nums(i)) {
                if (i + l + nums(i + l) >= boundary) {
                    boundary = i + l + nums(i + l)
                    tmp = i + l
                }
            }

            i = tmp
        }

        true
    }

    def main(args: Array[String]): Unit = {
//        输入：nums = [2,3,1,1,4]
//        输出：true
//        解释：可以先跳 1 步，从下标 0 到达下标 1, 然后再从下标 1 跳 3 步到达最后一个下标。
//        val nums = Array(2,3,1,1,4)
//        println(canJump(nums))

//        输入：nums = [3,2,1,0,4]
//        输出：false
//        解释：无论怎样，总会到达下标为 3 的位置。但该下标的最大跳跃长度是 0 ， 所以永远不可能到达最后一个下标。
        val nums = Array(3,2,1,0,4)
        println(canJump(nums))

    }
}
