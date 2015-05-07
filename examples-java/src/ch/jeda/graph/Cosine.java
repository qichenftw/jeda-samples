package ch.jeda.graph;

public class Cosine implements Function {

    @Override
    public double y(double x) {
        return Math.cos(x);
    }

    @Override
    public Function d() {
        return null;
    }

}
