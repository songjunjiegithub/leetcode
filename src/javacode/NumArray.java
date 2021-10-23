package javacode;

import java.util.Arrays;

public class NumArray {
    private int[] data;
    private int[] tree;
    public NumArray(int[] nums) {
        this.data = Arrays.copyOf(nums, nums.length);
        this.tree = new int[4 * nums.length];
        buildTree(0, 0, nums.length - 1);
    }

    public void update(int index, int val) {
        this.data[index] = val;
        update(0, 0, this.data.length - 1, index, val);
    }

    public void update(int index, int start, int end, int updateIndex, int value) {
        if (start == end) {
            this.tree[index] = value;
            return;
        }
        int mid = start + (end - start) / 2;
        int leftIndex = getLeftChild(index);
        int rightIndex = getRightChild(index);
        if (mid >= updateIndex) {
            update(leftIndex, start, mid, updateIndex, value);
        } else {
            update(rightIndex, mid + 1, end, updateIndex, value);
        }
        this.tree[index] = this.tree[leftIndex] + this.tree[rightIndex];
    }

    public int sumRange(int left, int right) {
        return sumRange(0, 0, this.data.length - 1, left, right);
    }

    public int sumRange(int index, int start, int end, int left, int right) {
        if (start == left && end == right) return this.tree[index];

        int mid = start + (end - start) / 2;
        int leftIndex = getLeftChild(index);
        int rightIndex = getRightChild(index);
        if (mid < left) {
            return sumRange(rightIndex, mid + 1, end, left, right);
        } else if (mid >= right) {
            return sumRange(leftIndex, start, mid, left, right);
        } else {
            return sumRange(leftIndex, start, mid, left, mid) + sumRange(rightIndex, mid + 1, end, mid + 1, right);
        }
    }

    public void buildTree(int index, int start, int end) {
        if (start == end) {
            this.tree[index] = this.data[start];
            return;
        }

        int leftIndex = getLeftChild(index);
        int rightIndex = getRightChild(index);
        int mid = start + (end - start) / 2;
        buildTree(leftIndex, start, mid);
        buildTree(rightIndex, mid + 1, end);
        this.tree[index] = this.tree[leftIndex] + this.tree[rightIndex];
    }

    public int getLeftChild(int index) {
        return 2 * index + 1;
    }

    public int getRightChild(int index) {
        return 2 * index + 2;
    }

    public static void main(String[] args) {
        NumArray numArray = new NumArray(new int[]{1, 3, 5});
        System.out.println(numArray.sumRange(0, 2)); // 返回 9 ，sum([1,3,5]) = 9
        numArray.update(1, 2);   // nums = [1,2,5]
        System.out.println(numArray.sumRange(0, 2)); // 返回 8 ，sum([1,2,5]) = 8
    }
}
