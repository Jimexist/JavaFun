package lambda.javafun.utils;

/**
 * Ported from http://www.opensource.apple.com/source/tcl/tcl-14/tcl/compat/strtod.c
 * <p/>
 * See http://www.opensource.apple.com/source/tcl/tcl-14/tcl/license.terms
 */
public class ByteUtils {

    final static int maxExponent = 511;	/* Largest possible base 10 exponent.  Any
                 * exponent larger than this will already
				 * produce underflow or overflow, so there's
				 * no need to worry about additional digits.
				 */
    final static double powersOf10[] = {	/* Table giving binary powers of 10.  Entry */
            10.,			/* is 10^2^i.  Used to convert decimal */
            100.,			/* exponents into floating-point numbers. */
            1.0e4,
            1.0e8,
            1.0e16,
            1.0e32,
            1.0e64,
            1.0e128,
            1.0e256
    };

    public static double strtod(final byte[] data, final int start, final int len) {
        if (len <= 0) {
            throw new NumberFormatException("Empty byte array with zero length");
        }

        final int end = start + len;

        int i = start;

        final boolean negative;
        if ('-' == data[i]) {
            negative = true;
            i += 1;
        } else {
            if ('+' == data[i]) {
                i += 1;
            }
            negative = false;
        }

        int intEnd = -1;
        int mantissaSize;
        for (mantissaSize = 0; i < end; mantissaSize += 1, i += 1) {
            final byte b = data[i];
            if (b < '0' || b > '9') {
                if ('.' != b || -1 != intEnd) {
                    break;
                }
                intEnd = mantissaSize;
            }
        }

        if (0 == mantissaSize) {
            return negative ? -0.0 : 0.0;
        }

        final int expStart = i;
        i -= mantissaSize;
        if (-1 == intEnd) { // pure integer
            intEnd = mantissaSize;
        } else {
            mantissaSize -= 1;
        }

        int fractionExp; // usually minus

        if (mantissaSize > 18) {
            fractionExp = intEnd - 18;
            mantissaSize = 18;
        } else {
            fractionExp = intEnd - mantissaSize;
        }


        int frac1 = 0;
        for (; mantissaSize > 9; mantissaSize -= 1) {
            byte b = data[i];
            i++;
            if ('.' == b) {
                b = data[i];
                i += 1;
            }
            frac1 = 10 * frac1 + (b - '0');
        }

        int frac2 = 0;
        for (; mantissaSize > 0; mantissaSize -= 1) {
            byte b = data[i];
            i++;
            if ('.' == b) {
                b = data[i];
                i++;
            }
            frac2 = 10 * frac2 + (b - '0');
        }

        double fraction = 1e9 * frac1 + frac2;

        int exp = 0;
        boolean negativeExp = false;
        i = expStart;
        if (i < end && ('e' == data[i] || 'E' == data[i])) {
            i++;
            if ('-' == data[i]) {
                negativeExp = true;
                i++;
            } else {
                if ('+' == data[i]) {
                    i++;
                }
                negativeExp = false;
            }
            while (i < end && data[i] >= '0' && data[i] <= '9') {
                exp = exp * 10 + (data[i] - '0');
                i++;
            }
        }

        if (negativeExp) {
            exp = fractionExp - exp;
        } else {
            exp = fractionExp + exp;
        }

        if (exp < 0) {
            negativeExp = true;
            exp = -exp;
        } else {
            negativeExp = false;
        }

        if (exp > maxExponent) {
            exp = maxExponent;
            // possible throw
        }

        double dblExp = 1.0;

        for (double d : powersOf10) {
            if (exp == 0)
                break;
            if ((exp & 1) != 0) {
                dblExp *= d;
            }
            exp >>= 1;
        }

        if (negativeExp) {
            fraction /= dblExp;
        } else {
            fraction *= dblExp;
        }

        return negative ? -fraction : fraction;
    }
}
