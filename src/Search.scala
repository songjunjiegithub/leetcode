object Search {
    def search(nums: Array[Int], target: Int): Boolean = {
        if (target == nums(0) || target == nums(nums.length - 1)) return true
        if (target < nums(0) && target > nums(nums.length - 1)) return false

        if (nums(0) < nums(nums.length - 1)) {
            var (k, m) = (0, nums.length - 1)
            while (k <= m) {
                val mid = (k + m) / 2
                if (nums(mid) == target) return true
                if (nums(mid) > target) {
                    m = mid - 1
                } else {
                    k = mid + 1
                }
            }
        } else {
            var (i, j) = (0, nums.length - 2)
            while (j >= 0 && nums(j) == nums(j + 1)) j -= 1

            if (target > nums(0)) {
                while (i <= j) {
                    val mid = (i + j) / 2
                    if (nums(mid) == target) return true

                    if (nums(mid) < nums(nums.length - 1)) {
                        j = mid - 1
                    } else if (nums(mid) > target) {
                        j = mid - 1
                    } else {
                        i = mid + 1
                    }
                }
            } else {
                while (i <= j) {
                    val mid = (i + j) / 2
                    if (nums(mid) == target) return true

                    if (nums(mid) >= nums(0)) {
                        i = mid + 1
                    } else if (nums(mid) > target) {
                        j = mid - 1
                    } else {
                        i = mid + 1
                    }
                }
            }
        }

        false
    }

    def main(args: Array[String]): Unit = {
        println(search(Array(1, 3, 5), 3))
    }
}
