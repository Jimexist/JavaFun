package com.leetcode.onlinejudge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ThreeSum {

    private static final ArrayList<ArrayList<Integer>> EMPTY = new ArrayList<ArrayList<Integer>>(0);
    private static final ArrayList<ArrayList<Integer>> ZERO_SINGLETON = new ArrayList<ArrayList<Integer>>(1);

    static {
        ZERO_SINGLETON.add(new Triple(0, 0, 0).toArrayList());
    }

    private static boolean isZeroSum(final int a, final int b, final int c) {
        assert a <= b && b <= c;
        return a + b + c == 0;
    }

    private ArrayList<ArrayList<Integer>> doThreeSum(final int[] num) {
        assert num.length >= 3;

        final Set<Triple> result = new HashSet<Triple>();

        for (int i = 0; i < num.length; ++i) {
            for (int j = i + 1; j < num.length; ++j) {
                for (int k = j + 1; k < num.length; ++k) {
                    final int a = num[i], b = num[j], c = num[k];
                    if (isZeroSum(a, b, c)) result.add(new Triple(a, b, c));
                }
            }
        }

        final ArrayList<ArrayList<Integer>> r = new ArrayList<ArrayList<Integer>>(result.size());
        for (Triple t : result) {
            r.add(t.toArrayList());
        }
        return r;
    }

    public ArrayList<ArrayList<Integer>> threeSum(int[] num) {
        if (num.length < 3) return EMPTY;
        Arrays.sort(num);
        final int signProduct = Integer.signum(num[0]) * Integer.signum(num[num.length - 1]);
        if (signProduct == 1) return EMPTY;
        else if (signProduct == 0) {
            if ((num[0] == 0 && num[2] == 0) || (num[num.length - 1] == 0 && num[num.length - 3] == 0))
                return ZERO_SINGLETON;
            else
                return EMPTY;
        } else
            return doThreeSum(num);
    }

    public final static class Triple {
        private final int a;
        private final int b;
        private final int c;
        private final ArrayList<Integer> tuple = new ArrayList<Integer>(3);

        public Triple(final int a, final int b, final int c) {
            assert a <= b && b <= c;
            this.a = a;
            this.b = b;
            this.c = c;
            tuple.add(a);
            tuple.add(b);
            tuple.add(c);
        }

        public ArrayList<Integer> toArrayList() {
            return tuple;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Triple triple = (Triple) o;

            return (a == triple.a) && (b == triple.b) && (c == triple.c);
        }

        @Override
        public int hashCode() {
            int result = a;
            result = 31 * result + b;
            result = 31 * result + c;
            return result;
        }
    }
}
