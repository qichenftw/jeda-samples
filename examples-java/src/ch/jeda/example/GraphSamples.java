package ch.jeda.example;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.graph.Exp;
import ch.jeda.graph.Function;
import ch.jeda.graph.GraphView;
import ch.jeda.graph.Polynom;
import ch.jeda.graph.Sine;
import ch.jeda.ui.*;

public class GraphSamples extends Program {

    GraphView fenster;

    @Override
    public void run() {
        fenster = new GraphView();
        fenster.drawGrid();
        fenster.drawAxes();

        fenster.setColor(Color.BLUE);
        Function f = new Polynom(0.16, 0.12, -1.5, -0.6, 2.0, 0.0);
        fenster.drawGraph(f);
        fenster.drawText(-5, 5, f.toString());

        fenster.setColor(Color.MAROON);
        fenster.drawGraph(f.d());
        fenster.drawText(-5, 4, f.d().toString());

        fenster.setColor(Color.RED);
        Function g = new Sine();
        fenster.drawGraph(g);

        fenster.setColor(Color.GREEN);
        fenster.drawGraph(new Exp());

        fenster.drawPoint(Math.PI, g.y(Math.PI));
    }
}
