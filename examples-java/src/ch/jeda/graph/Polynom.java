package ch.jeda.graph;

public class Polynom implements Function {

    private final double[] params;

    public Polynom(final double... params) {
        this.params = params;
    }

    @Override
    public Function d() {
        double[] dParams = new double[params.length - 1];
        int n = params.length - 1;
        while (n > 0) {
            dParams[dParams.length - n] = n * a(n);
            --n;
        }

        return new Polynom(dParams);
    }

    @Override
    public double y(final double x) {
        double result = 0.0;
        int n = 0;
        while (n < params.length) {
            result = result + a(n) * Math.pow(x, n);
            ++n;
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        int n = params.length - 1;
        while (n >= 0) {
            boolean positive = a(n) > 0;
            double a = Math.abs(a(n));
            if (a > 1e-10) {
                if (result.length() > 0) {
                    if (positive) {
                        result.append('+');
                    }
                    else {
                        result.append('-');
                    }
                }

                result.append(a);
                if (n > 0) {
                    result.append("*x");
                    if (n > 1) {
                        result.append('^');
                        result.append(n);
                    }
                }
            }

            --n;
        }

        return result.toString();
    }

    private double a(int n) {
        return params[params.length - n - 1];
    }
}
