package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KnapsackProblem {
    int[] db;
    public int dfs(int[] ws, int[] vs, int w) {
        return 0;
    }


    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String one = bufferedReader.readLine();
            int n = Integer.parseInt(one.split(" ")[0]);
            int w = Integer.parseInt(one.split(" ")[1]);
            int[] ws = new int[n];
            int[] vs = new int[n];
            for (int i = 0; i < n; i++) {
                String line = bufferedReader.readLine();
                ws[i] = Integer.parseInt(line.split(" ")[0]);
                vs[i] = Integer.parseInt(line.split(" ")[1]);
            }
//            System.out.println(dfs(ws, vs, w));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
