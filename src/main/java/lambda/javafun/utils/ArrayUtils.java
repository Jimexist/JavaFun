package lambda.javafun.utils;

import java.util.Comparator;

public class ArrayUtils {

    private static final int CUTOFF = 3;

    public static <T> int firstBiggerThan(final T[] list, final T threshold, final Comparator<T> comp) {
        if (comp.compare(list[0], threshold) > 0) {
            return 0;
        } else if (comp.compare(list[list.length - 1], threshold) <= 0) {
            return -1;
        } else {
            return firstBiggerThan(list, threshold, comp, 0, list.length);
        }
    }

    protected static <T> int firstBiggerThan(final T[] list, final T threshold, final Comparator<T> comp, final int start, final int end) {
        final int distance = end - start;
        if (distance <= CUTOFF) {
            for (int i = start; i < end; ++i) {
                final T o1 = list[i];
                if (comp.compare(o1, threshold) > 0)
                    return i;
            }
            return -1;
        } else {
            final int middle = start + distance / 2;
            final T middleT = list[middle];
            if (comp.compare(middleT, threshold) <= 0) {
                return firstBiggerThan(list, threshold, comp, middle, end);
            } else {
                return firstBiggerThan(list, threshold, comp, start, middle);
            }
        }
    }

    public static <T> int lastSmallerThan(final T[] list, final T threshold, final Comparator<T> comp) {
        if (comp.compare(list[list.length - 1], threshold) < 0) {
            return list.length;
        }

        final int firstBiggerThan = firstBiggerThan(list, threshold, comp);
        if (firstBiggerThan == -1) {
            return -1;
        } else {
            return firstBiggerThan - 1;
        }
    }

}
