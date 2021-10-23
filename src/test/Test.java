package test;

import javacode.ListNode;
import javacode.TreeNode;
import java.util.*;


public class Test {
    private class ListNode {
        int x1;
        int y1;
        int x2;
        int y2;
        ListNode next;
        public ListNode(){}
        public ListNode(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        public int area() {
            return (x2 - x1 + 1) * (y2 - y1 + 1);
        }
    }
    int len;
    int m;
    int n;
    ListNode head;
    Random rand;
    public Test(int m, int n) {
        head = new ListNode();
        this.m = m;
        this.n = n;
        this.len = m * n;
        this.rand = new Random();
        reset();
    }

    public int[] flip() {
        int index = rand.nextInt(len) + 1;
        int sum = 0;
        ListNode ele = head.next;
        ListNode pre = head;
        while (sum + ele.area() < index) {
            sum += ele.area();
            pre = ele;
            ele = ele.next;
        }

        int x = (index - sum - 1) / (ele.y2 - ele.y1 + 1) + ele.x1;
        int y = (index - sum - 1) % (ele.y2 - ele.y1 + 1) + ele.y1;

        ListNode cur = pre;
        if (x > ele.x1) {
            cur.next = new ListNode(ele.x1, ele.y1, x - 1, ele.y2);
            cur = cur.next;
        }

        if (y > ele.y1) {
            cur.next = new ListNode(x, ele.y1, x, y - 1);
            cur = cur.next;
        }

        if (y < ele.y2) {
            cur.next = new ListNode(x, y + 1, x, ele.y2);
            cur = cur.next;
        }

        if (x < ele.x2) {
            cur.next = new ListNode(x + 1, ele.y1, ele.x2, ele.y2);
            cur = cur.next;
        }

        cur.next = ele.next;

        len--;
        return new int[]{x, y};
    }

    public void reset() {
        head.next = new ListNode(0, 0, m - 1, n - 1);
        len = m * n;
    }



    public static void main(String[] args) {
        Test test = new Test(3, 1);
        System.out.println(Arrays.toString(test.flip()));
        System.out.println(Arrays.toString(test.flip()));
        System.out.println(Arrays.toString(test.flip()));
        test.reset();
        System.out.println(Arrays.toString(test.flip()));
    }
}
