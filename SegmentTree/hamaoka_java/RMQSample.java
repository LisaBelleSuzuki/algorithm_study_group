package SegmentTree.hamaoka_java;

import java.util.Arrays;
import java.util.Scanner;

public class RMQSample {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int q = sc.nextInt();
        sc.nextLine();
        sc.close();
        System.out.println("answer");

        // sample
        int[] nums = new int[] { 4, 5, 3, 7, 9, 6, 4, 1, 2 };
        SegmentTree st = new SegmentTree(nums);
        int min = st.query(7, 8);
        System.out.println(min);
    }
}

class SegmentTree {

    int N;
    int[] originalData;
    int[] tree;

    final int INT_MAX = Integer.MAX_VALUE / 2;

    SegmentTree(int[] original) {
        this.originalData = Arrays.copyOf(original, original.length);
        int n = originalData.length;
        this.N = 1;
        while (N < n)
            N *= 2;
        this.tree = new int[N * 2];
        for (int i = 0; i < N * 2; i++) {
            tree[i] = INT_MAX;
        }
        build();
    }

    // O(N)
    void build() {
        for (int i = 0; i < originalData.length; i++) {
            update(i, originalData[i]);
        }
    }

    // i番目の値をxに更新 O(log N)
    void update(int i, int x) {
        i = N + i - 1;
        tree[i] = x;
        while (i > 0) {
            i = (i - 1) / 2;
            tree[i] = marge(tree[i * 2 + 1], tree[i * 2 + 2]);
        }
    }

    // O(log N);
    int query(int start, int end) {
        return query(start, end, 0, 0, N);
    }

    // [a, b)の最小値、l, rにはノードkに対応する区間を与える
    private int query(int a, int b, int k, int l, int r) {
        if (r <= a || b <= l)
            return INT_MAX;

        if (a <= l && r <= b) {
            return tree[k];
        } else {
            int lv = query(a, b, 2 * k + 1, l, (l + r) / 2);
            int rb = query(a, b, 2 * k + 2, (l + r) / 2, r);
            return marge(lv, rb);
        }
    }

    private int marge(int l, int r) {
        return Math.min(l, r);
    }
}
