package javacode;

public class NumMatrix2 {
    int[][] matrix;
    int[][] tree;
    public NumMatrix2(int[][] matrix) {
        int[][] sums = new int[matrix.length + 1][matrix[0].length + 1];
        this.matrix = new int[matrix.length][matrix[0].length];
        this.tree = new int[matrix.length + 1][matrix[0].length + 1];
        for (int i = 1; i < sums.length; i++) {
            for (int j = 1; j < sums[0].length; j++) {
                this.matrix[i - 1][j - 1] = matrix[i - 1][j - 1];
                sums[i][j] = sums[i - 1][j] + sums[i][j - 1] + matrix[i - 1][j - 1] - sums[i - 1][j - 1];
                this.tree[i][j] = sums[i][j] - sums[i - (i & -i)][j] - sums[i][j - (j & -j)] + sums[i - (i & -i)][j - (j & -j)];
            }
        }
    }

    public void set(int row, int col, int val) {
        for (; row < this.tree.length; row += (row & -row)) {
            for (int j = col; j < this.tree[0].length; j += (j & -j)) {
                this.tree[row][j] += val;
            }
        }
    }

    public int sums(int row, int col) {
        int result = 0;
        for(; row > 0; row -= (row & -row)) {
            for (int j = col; j > 0; j -= (j & -j)) {
                result += this.tree[row][j];
            }
        }

        return result;
    }

    public void update(int row, int col, int val) {
        int v = val - this.matrix[row][col];
        this.matrix[row][col] = val;
        set(row + 1, col + 1, v);
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        return sums(row2 + 1, col2 + 1) - sums(row1, col2 + 1) - sums(row2 + 1, col1) + sums(row1, col1);
    }

    public static void main(String[] args) {
//        NumMatrix2 numMatrix = new NumMatrix2(new int[][]{{3, 0, 1, 4, 2}, {5, 6, 3, 2, 1}, {1, 2, 0, 1, 5}, {4, 1, 0, 1, 7}, {1, 0, 3, 0, 5}});
        NumMatrix2 numMatrix = new NumMatrix2(new int[][]{{1}});
        System.out.println(numMatrix.sumRegion(0, 0, 0, 0)); // 返回 8 (即, 左侧红色矩形的和)
        numMatrix.update(0, 0, -1);       // 矩阵从左图变为右图
        System.out.println(numMatrix.sumRegion(0, 0, 0, 0)); // 返回 10 (即，右侧红色矩形的和)\
    }
}
