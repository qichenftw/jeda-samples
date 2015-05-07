package ch.jeda.graph;

public class Exp implements Function {

    @Override
    public Function d() {
        return this;
    }

    @Override
    public double y(final double x) {
        return Math.exp(x);
    }

    @Override
    public String toString() {
        return "e^x";
    }
}
