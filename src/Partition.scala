object Partition {
    def partition(head: ListNode, x: Int): ListNode = {
        val lt = new ListNode(0, null)
        val gt = new ListNode(0, null)
        var (lt_tmp, gt_tmp, ele) = (lt, gt, head)

        while (ele != null) {
            if (ele.x < x) {
                lt_tmp.next = ele
                lt_tmp = ele
            } else {
                gt_tmp.next = ele
                gt_tmp = ele
            }

            ele = ele.next
        }

        gt_tmp.next = null
        lt_tmp.next = gt.next

        lt.next
    }
}
