package lambda.javafun.utils;

import java.util.Iterator;

public final class FunUtils {

    public static <A> void foreach(final Iterable<A> list, final Func1v<A> feach) {
        for (A a : list) {
            feach.apply(a);
        }
    }

    /**
     * JVM does not eliminate tail-recursion, so loop...
     *
     * @param list list to loop over
     * @param func func to fold
     * @param init init value
     * @param <A>  result type
     * @param <B>  element type
     * @return folded result
     */
    public static <A, B> A foldl(final Iterable<B> list, final Func2<A, B, A> func, A init) {
        for (B b : list) {
            init = func.apply(init, b);
        }
        return init;
    }

    public static <A> A foldl1(final Iterable<A> list, final Func2<A, A, A> func) {
        final Iterator<A> i = list.iterator();
        A init = i.next();
        while (i.hasNext()) {
            init = func.apply(init, i.next());
        }
        return init;
    }

    public static <A, B> Iterable<B> map(final Iterable<A> list, final Func1<A, B> fmap) {
        return new Iterable<B>() {
            final Iterator<B> i = new Iterator<B>() {
                final Iterator<A> ia = list.iterator();

                @Override
                public boolean hasNext() {
                    return ia.hasNext();
                }

                @Override
                public B next() {
                    return fmap.apply(ia.next());
                }

                @Override
                public void remove() {
                    ia.next();
                }
            };

            @Override
            public Iterator<B> iterator() {
                return i;
            }
        };
    }

    public interface Func1<Input, Output> {
        public Output apply(final Input input);
    }

    public interface Func2<I1, I2, O> {
        public O apply(final I1 i1, final I2 i2);

        public Func1<I2, O> partialApply(final I1 i1);
    }

    public interface Func1v<Input> {
        public void apply(final Input input);
    }
}
