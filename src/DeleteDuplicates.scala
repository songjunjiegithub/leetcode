object DeleteDuplicates {
    def deleteDuplicates(head: ListNode): ListNode = {
        if (head == null || head.next == null) return head

        val hair = new ListNode(0, head)

        var (one, two, value) = (hair, head, head.x)

        while (two != null) {
            if (two.next == null || two.next.x != value) {
                one.next = two
                one = two
                two = two.next
                if (two != null) value = two.x
            } else {
                while (two != null && two.x == value) two = two.next
                if (two != null) value = two.x
            }
        }

        one.next = null

        hair.next
    }

    // 83.删除排序链表中的重复元素
    def deleteDuplicatess(head: ListNode): ListNode = {
        if (head == null || head.next == null) return head
        var (tail, ele) = (head, head.next)

        while (ele != null) {
            if (tail.x != ele.x) {
                tail.next = ele
                tail = ele
            }

            ele = ele.next
        }

        tail.next = null

        head
    }

    def main(args: Array[String]): Unit = {
        //    输入：head = [1,2,3,3,4,4,5]
        //    输出：[1,2,5]
        val arr = Array(1,2,3,3,4,4,5)
        var tmp: ListNode = null
        for (i <- 0 until arr.length) {
            tmp = new ListNode(arr(arr.length - 1 - i), tmp)
        }

        println(deleteDuplicates(tmp))
    }
}
