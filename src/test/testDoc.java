package test;

import java.util.HashMap;

/**
 * @author junjie
 * @date 2021年09月15日 12:56
 */
public class testDoc {
    HashMap<Integer, int[]> map = new HashMap<>();

    /**
     *
     * @author jitwxs
     * @date 2021/9/15 12:57
     * @param n
     * @return int[]
     */
    public int[] getMoneyAmount(int n) {
        if (map.containsKey(n)) return map.get(n);
        if (n == 1) {
            map.put(1, new int[0]);
            return map.get(1);
        }

        int sum = n * (n + 1) / 2;
        int[] result = new int[0];
        for (int i = 1; i <= n; i++) {
            int st = i;
            int[] t1 = i == 1 ? new int[0] : getMoneyAmount(i - 1);
            int[] t2 = i == n ? new int[0] : getMoneyAmount(n - i);
            int s1 = 0;
            for (int i1 : t1) {
                s1 += i1;
            }

            int s2 = 0;
            for (int i1 : t2) {
                s2 += i1 + i;
            }

            st += Math.max(s1, s2);

            if (st <= sum) {
                sum = st;
                result = new int[t1.length + t2.length + 1];
                result[0] = i;
                System.arraycopy(t1, 0, result, 1, t1.length);

                for (int j = 0; j < t2.length; j++) {
                    result[t1.length + 1 + j] = t2[j] + i;
                }
            }
        }

        map.put(n, result);
        return result;
    }
}
