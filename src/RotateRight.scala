object RotateRight {
    def rotateRight(head: ListNode, k: Int): ListNode = {
        if (head == null || head.next == null || k == 0) return head

        var j = 0
        var (front, after) = (head, head)
        while (front != null && j < k) {
            front = front.next
            j += 1
        }

        if (front == null) {
            front = head
            var l = 0
            while (l < k % j) {
                front = front.next
                l += 1
            }
        }

        while (front.next != null) {
            front = front.next
            after = after.next
        }

        front.next = head
        front = after.next
        after.next = null

        front
    }

    def main(args: Array[String]): Unit = {
        //        输入：head = [1,2,3,4,5], k = 2
        //        输出：[4,5,1,2,3]
        var head = rotateRight(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, null))))), 2)
        while (head != null) {
            print(head.x + ",")
            head = head.next
        }

        //        输入：head = [0,1,2], k = 4
        //        输出：[2,0,1]
    }
}
