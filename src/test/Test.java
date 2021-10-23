package test;

import javacode.ListNode;
import javacode.TreeNode;
import java.util.*;


public class Test {




    public static void main(String[] args) {
        Test test = new Test(3, 1);
        System.out.println(Arrays.toString(test.flip()));
        System.out.println(Arrays.toString(test.flip()));
        System.out.println(Arrays.toString(test.flip()));
        test.reset();
        System.out.println(Arrays.toString(test.flip()));
    }
}
