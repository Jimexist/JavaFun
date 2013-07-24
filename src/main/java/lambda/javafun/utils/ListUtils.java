package lambda.javafun.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class ListUtils {

    public static <T> T collectFirst(final Iterable<T> list, final Predicate<T> predicate) {
        for (T element : list) {
            if (predicate.apply(element)) {
                return element;
            }
        }
        return null;
    }

    public static <A, B> Iterable<B> map(final Iterable<A> list, final F1<A, B> func) {
        return new Iterable<B>() {

            @Override
            public Iterator<B> iterator() {
                return new Iterator<B>() {
                    private Iterator<A> i = list.iterator();

                    @Override
                    public boolean hasNext() {
                        return i.hasNext();
                    }

                    @Override
                    public B next() {
                        return func.apply(i.next());
                    }

                    @Override
                    public void remove() {
                        i.next();
                    }
                };
            }
        };
    }

    public static void main(String[] args) {
        final List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6);

        final Integer result = collectFirst(integers, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer e) {
                return e.compareTo(3) > 0;
            }
        });

        System.out.println(String.format("The first integer larger than 3 is %d", result));

        final Iterable<Integer> results = map(integers, new F1<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return integer * integer;
            }
        });

        System.out.println("The squares of them are: ");
        for (Integer i : results) {
            System.out.print(i + " ");
        }
    }

    public interface Predicate<T> {
        boolean apply(final T e);
    }

    public interface F1<A, B> {
        B apply(final A a);
    }
}
