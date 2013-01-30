package com.leetcode.onlinejudge;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class ThreeSumTest {

    private final ThreeSum threeSum = new ThreeSum();
    private final ArrayList<ArrayList<Integer>> EMPTY = new ArrayList<ArrayList<Integer>>();
    private final ArrayList<ArrayList<Integer>> ZERO = new ArrayList<ArrayList<Integer>>(1);
    private final ArrayList<ArrayList<Integer>> ONE = new ArrayList<ArrayList<Integer>>(1);
    private final ArrayList<ArrayList<Integer>> ONE2 = new ArrayList<ArrayList<Integer>>(1);
    private final ArrayList<ArrayList<Integer>> THREE = new ArrayList<ArrayList<Integer>>(3);

    {
        ZERO.add(new ThreeSum.Triple(0, 0, 0).toArrayList());

        ONE.add(new ThreeSum.Triple(-1, -1, 2).toArrayList());
        ONE2.add(new ThreeSum.Triple(-2, 1, 1).toArrayList());


        THREE.add(new ThreeSum.Triple(-1, 0, 1).toArrayList());
        THREE.add(new ThreeSum.Triple(-2, 0, 2).toArrayList());
        THREE.add(new ThreeSum.Triple(-2, 1, 1).toArrayList());
    }

    private static void ArrayListEquals(final ArrayList<ArrayList<Integer>> lhs, final ArrayList<ArrayList<Integer>> rhs) {
        final Comparator<ArrayList<Integer>> comp = new Comparator<ArrayList<Integer>>() {
            @Override
            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
                return Arrays.deepToString(o1.toArray()).compareTo(Arrays.deepToString(o2.toArray()));
            }
        };

        Collections.sort(lhs, comp);
        Collections.sort(rhs, comp);
        org.junit.Assert.assertEquals(lhs, rhs);
    }

    @org.junit.Test
    public void testThreeSum() throws Exception {
        ArrayListEquals(threeSum.threeSum(new int[]{}), EMPTY);
        ArrayListEquals(threeSum.threeSum(new int[]{0}), EMPTY);
        ArrayListEquals(threeSum.threeSum(new int[]{0, 0, 0}), ZERO);
        ArrayListEquals(threeSum.threeSum(new int[]{0, 0, 0, 0, 0, 0}), ZERO);
        ArrayListEquals(threeSum.threeSum(new int[]{1, 0, 0}), EMPTY);
        ArrayListEquals(threeSum.threeSum(new int[]{-1, 2, -1}), ONE);
        ArrayListEquals(threeSum.threeSum(new int[]{1, -2, 1}), ONE2);
        ArrayListEquals(threeSum.threeSum(new int[]{1, 0, -2, 1, -1, 2}), THREE);
        ArrayListEquals(threeSum.threeSum(new int[]{1, 0, -2, 1, -1, 2, 9, 21}), THREE);
        ArrayListEquals(threeSum.threeSum(new int[]{1, 0, -2, 1, -1, 2}), THREE);
    }
}
