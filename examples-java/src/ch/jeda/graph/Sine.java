package ch.jeda.graph;

public class Sine implements Function {

    @Override
    public Function d() {
        return new Cosine();
    }

    @Override
    public double y(final double x) {
        return Math.sin(x);
    }

    @Override
    public String toString() {
        return "sin(x)";
    }

}
