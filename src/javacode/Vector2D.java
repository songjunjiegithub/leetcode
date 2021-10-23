package javacode;

public class Vector2D {
    int i;
    int j;
    int[][] vec;
    public Vector2D(int[][] vec) {
        this.vec = vec;
        i = 0;
        j = -1;
    }

    public int next() {
        if (j == vec[i].length - 1) {
            i++;
            j = 0;
            return vec[i][j];
        }

        return vec[i][++j];
    }

    public boolean hasNext() {
        return vec.length != 0 && (i != vec.length - 1 || j != vec[i].length - 1);
    }

    public static void main(String[] args) {
//        int[][] ints = {{1, 2}, {3}, {4}};
//        Vector2D iterator = new Vector2D(ints);
//        iterator.next(); // 返回 1
//        iterator.next(); // 返回 2
//        iterator.next(); // 返回 3
//        iterator.hasNext(); // 返回 true
//        iterator.hasNext(); // 返回 true
//        iterator.next(); // 返回 4
//        iterator.hasNext(); // 返回 false
        int i = 0;
        System.out.println(++i < 2 && i < 1);
    }
}
