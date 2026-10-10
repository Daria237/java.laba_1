public class Complex {
    private final double re;
    private final double im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public Complex add(Complex other) {
        return new Complex(re + other.re, im + other.im);
    }

    public Complex subtract(Complex other) {
        return new Complex(re - other.re, im - other.im);
    }

    public Complex multiply(Complex other) {
        double newRe = re * other.re - im * other.im;
        double newIm = re * other.im + im * other.re;
        return new Complex(newRe, newIm);
    }

    public Complex divide(Complex other) {
        double d = other.re * other.re + other.im * other.im;
        if (d == 0) {
            throw new ArithmeticException("Деление на ноль");
        }
        double newRe = (re * other.re + im * other.im) / d;
        double newIm = (im * other.re - re * other.im) / d;
        return new Complex(newRe, newIm);
    }

    public boolean isZero() {
        return Math.abs(re) < 1e-9 && Math.abs(im) < 1e-9;
    }

        public static Complex parse(String s) {
        s = s.trim().replace(" ", "");
        if (!s.endsWith("i")) {
            return new Complex(Double.parseDouble(s), 0);
        }
        s = s.substring(0, s.length() - 1);
        int pos = -1;
        for (int k = s.length() - 1; k > 0; k--) {
            if (s.charAt(k) == '+' || s.charAt(k) == '-') {
                pos = k;
                break;
            }
        }
        if (pos == -1) {
            throw new NumberFormatException("пишите число полностью, например 0+2i");
        }
        double re = Double.parseDouble(s.substring(0, pos));
        double im = Double.parseDouble(s.substring(pos));
        return new Complex(re, im);
    }

    @Override
    public String toString() {
        String sign = im < 0 ? "-" : "+";
        return fmt(re) + sign + fmt(Math.abs(im)) + "i";
    }

    private static String fmt(double x) {
        x = Math.round(x * 10000) / 10000.0;
        if (x == (long) x) {
            return String.valueOf((long) x);
        }
        return String.valueOf(x);
    }
}
