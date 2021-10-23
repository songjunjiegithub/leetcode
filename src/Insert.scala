object Insert {
    def insert(intervals: Array[Array[Int]], newInterval: Array[Int]): Array[Array[Int]] = {
        val result = scala.collection.mutable.ListBuffer.empty[Array[Int]]

        var Array(left, right) = newInterval

        var i = 0
        while (i < intervals.length && intervals(i)(1) < left) {
            result.addOne(intervals(i))
            i += 1
        }

        while (i < intervals.length && intervals(i)(0) <= right) {
            left = Math.min(left, intervals(i)(0))
            right = Math.max(right, intervals(i)(1))
            i += 1
        }

        result.addOne(Array(left, right))

        while (i < intervals.length) {
            result.addOne(intervals(i))
            i += 1
        }

        result.toArray
    }
}
