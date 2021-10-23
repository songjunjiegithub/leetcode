object Jump {
    def jump(nums: Array[Int]): Int = {
        if (nums.length <= 1) return 0
        if (nums(0)>=nums.length-1) return 1

        var boundary = nums(0)
        var i = 0
        var result = 0
        var tmp = 0
        while (boundary < nums.length - 1) {
            if (nums(i) == 0) return -1
            for (l <- 0 to nums(i)) {
                if (i + l + nums(i + l) >= boundary) {
                    tmp = i + l;
                    boundary = tmp + nums(tmp)
                }
            }
            i = tmp
            result += 1
        }

        result + 1
    }

    def main(args: Array[String]): Unit = {
//        输入: [2,3,1,1,4]
//        输出: 2
//        解释: 跳到最后一个位置的最小跳跃数是 2。
        var nums = Array(2,3,1,1,4)
        println(jump(nums))

    }
}
