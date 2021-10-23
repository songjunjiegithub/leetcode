object ReverseBetween {
    def reverseBetween(head: ListNode, left: Int, right: Int): ListNode = {
        if (left == right) return head

        val hair = new ListNode(0, head)

        dfs(hair, left, right, 0)

        hair.next
    }

    def dfs(head: ListNode, left: Int, right: Int, index: Int): ListNode = {
        if (index >= right) return head

        val result = dfs(head.next, left, right, index + 1)

        if (index >= left && index < right) {
            val next = head.next
            head.next = head.next.next
            next.next = head
            return result
        }
        if (index == left - 1) {
            head.next = result
            return head
        }

        head
    }
}
