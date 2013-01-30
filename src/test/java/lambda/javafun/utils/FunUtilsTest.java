package lambda.javafun.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static lambda.javafun.utils.FunUtils.*;

public class FunUtilsTest {

    final private Iterable<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
    final private Iterable<Long> longs = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L);
    final private Func2<Long, Long, Long> multiplier = new Func2<Long, Long, Long>() {
        @Override
        public Long apply(Long aLong, Long aLong2) {
            return aLong * aLong2;
        }

        @Override
        public Func1<Long, Long> partialApply(final Long aLong) {
            return new Func1<Long, Long>() {
                private final Long l = aLong;

                @Override
                public Long apply(Long aLong) {
                    return this.l * aLong;
                }
            };
        }
    };

    final Func2<Integer, Integer, Integer> adder = new Func2<Integer, Integer, Integer>() {
        @Override
        public Integer apply(Integer integer, Integer integer2) {
            return integer + integer2;
        }

        @Override
        public Func1<Integer, Integer> partialApply(final Integer integer) {
            return new Func1<Integer, Integer>() {
                private final Integer i = integer;

                @Override
                public Integer apply(Integer integer) {
                    return this.i + integer;
                }
            };
        }
    };

    @Test
    public void testPartialApply() throws Exception {
        final Func1<Integer, Integer> acc = adder.partialApply(1);
        final Func1<Long, Long> doubler = multiplier.partialApply(2L);

        Assert.assertEquals(acc.apply(42), new Integer(43));
        Assert.assertEquals(acc.apply(-1), new Integer(0));

        Assert.assertEquals(doubler.apply(42L), new Long(84));
        Assert.assertEquals(doubler.apply(1L), new Long(2));
    }

    @Test
    public void testFoldl() throws Exception {
        Assert.assertEquals(foldl(integers, adder, 0), new Integer(45));
        Assert.assertEquals(foldl(longs, multiplier, 1L), new Long(1 * 2 * 3 * 4 * 5 * 6 * 7 * 8 * 9));
    }

    @Test
    public void testFoldl1() throws Exception {
        Assert.assertEquals(foldl(integers, adder, 0), foldl1(integers, adder));
        Assert.assertEquals(foldl(longs, multiplier, 1L), foldl1(longs, multiplier));
    }

    @Test
    public void testForeach() throws Exception {
        foreach(integers, new Func1v<Integer>() {
            @Override
            public void apply(Integer input) {
                System.out.println("foreach: " + input);
            }
        });

    }

    @Test
    public void testMap() throws Exception {
        System.out.println("testing map");
        foreach(map(integers, new Func1<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return integer * integer;
            }
        }), new

                Func1v<Integer>() {
                    @Override
                    public void apply(Integer input) {
                        System.out.println(input);
                    }
                });
    }
}
