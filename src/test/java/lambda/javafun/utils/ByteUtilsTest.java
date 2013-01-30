package lambda.javafun.utils;

import org.junit.Assert;
import org.junit.Test;

public class ByteUtilsTest {

    static double[] exactData = {
            -0, +0,
            0e1,
            0e308,
            1e308,
            -1e2,
            Double.MAX_VALUE,
            -Double.MAX_VALUE,
            1.82,
            21312312,
            1.33,
            -1.33,
            1.33333333e0000001,
            -1.33333333e0000001,
            9876543211234567890987654321D,
            -9876543211234567890987654321D,
            .9876543211234567890987654321,
            -.9876543211234567890987654321,
            Math.PI,
            -Math.PI,
            Math.E,
            -Math.E,
            123456789.98765432,
            -123456789.98765432,
            23456789.987654321,
            -23456789.987654321,
    };

    static double[] nonExactData = {
            923456789.987654321,
            -923456789.987654321,
            123456789.9876543212,
            -923456789.9876543212,
            Double.MIN_VALUE,
            -Double.MIN_VALUE,
            Double.MIN_NORMAL,
            -Double.MIN_NORMAL,
    };

    @Test
    public void testExact() {
        for (double d : exactData) {
            final String s = String.valueOf(d);
            final byte[] b = s.getBytes();
            final double d1 = ByteUtils.strtod(b, 0, b.length);
            final double d2 = Double.parseDouble(s);
            Assert.assertEquals(d1, d, 0);
            Assert.assertEquals(d2, d, 0);
            Assert.assertEquals(d1, d2, 0);
        }
    }

    @Test
    public void testNonExact() throws Exception {
        for (double d : nonExactData) {
            final String s = String.valueOf(d);
            final byte[] b = s.getBytes();
            final double d1 = ByteUtils.strtod(b, 0, b.length);
            final double d2 = Double.parseDouble(s);
            final double delta = Math.max(Math.abs(d * 1.5e-16), 1e-307);
//            System.out.println(String.format("Real [%s], test [%s], library [%s], delta: [%s]",
//                    s,
//                    String.valueOf(d1),
//                    String.valueOf(d2),
//                    String.valueOf(delta)));
            Assert.assertEquals(d1, d, delta);
            Assert.assertEquals(d2, d, 0);
            Assert.assertEquals(d1, d2, delta);
            Assert.assertNotEquals(d1, d, 0);
            Assert.assertNotEquals(d1, d2, 0);
        }

    }
}
