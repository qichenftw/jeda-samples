package ch.jeda.graph;

import ch.jeda.Convert;
import ch.jeda.ui.Color;
import ch.jeda.ui.Window;
import ch.jeda.ui.WindowFeature;

public class GraphView {

    private static final Color DARK_GRAY = new Color(60, 60, 60);
    private static final int CROSS_SIZE = 10;
    private static final int TEXT_OFFSET = 7;
    private static final int POINT_RADIUS = 5;
    private final Window window;
    private double maxX;
    private double maxY;
    private double minX;
    private double minY;
    private PointShape pointShape;

    public GraphView() {
        this(800, 800);
    }

    public GraphView(final int width, final int height) {
        this(new Window(width, height));
    }

    public GraphView(final int width, final int height, final WindowFeature... features) {
        this(new Window(width, height, features));
    }

    private GraphView(final Window window) {
        this.window = window;
        this.window.setAntiAliasing(true);
        this.window.setLineWidth(1.0);
        this.minX = -5.0;
        this.maxX = 5.0;
        this.minY = -5.0;
        this.maxY = 5.0;
        this.pointShape = PointShape.Circle;
    }

    public final void clear() {
        this.setColor(Color.WHITE);
        this.window.fill();
    }

    public final void drawAxes() {
        int x;
        int y;

        this.setColor(DARK_GRAY);

        x = 0;
        while (x <= this.maxX) {
            x = x + 1;
            this.drawText(x, 0.0, Convert.toString(x));
        }

        x = 0;
        while (x >= this.minX) {
            x = x - 1;
            this.drawText(x, 0.0, Convert.toString(x));
        }

        y = 0;
        while (y <= this.maxY) {
            y = y + 1;
            this.drawText(0.0, y, Convert.toString(y));
        }

        y = 0;
        while (y >= this.minY) {
            y = y - 1;
            this.drawText(0.0, y, Convert.toString(y));
        }

        this.setColor(Color.BLACK);
        // Mark 1 on x-axis
        this.drawLine(1.0, -0.3, 1.0, 0.3);
        // Mark 1 on y-axis
        this.drawLine(-0.3, 1.0, 0.3, 1.0);
        this.drawLine(this.minX, 0.0, this.maxX, 0.0);
        this.drawLine(0.0, this.minY, 0.0, this.maxY);
    }

    public final void drawCoordinates(final double x, final double y) {
        this.drawText(x, y, "(" + x + ", " + y + ")");
    }

    public final void drawGraph(final double startX, final double endX, final double[] y) {
        double lastX = startX;
        double stepX = (endX - startX) / (y.length - 1);
        double x = lastX + stepX;
        for (int i = 1; i < y.length; ++i) {
            this.drawLine(lastX, y[i - 1], x, y[i]);
            System.out.println("x=" + x + ", y=" + y[i]);
            this.drawPoint(x, y[i]);
            lastX = x;
            x = x + stepX;
        }

    }

    public final void drawGraph(final Function function) {
        double lastX;
        double lastY;
        double x = this.minX;
        double y = function.y(x);
        while (x < this.maxX) {
            lastX = x;
            lastY = y;
            x = x + 0.0001;
            y = function.y(x);
            this.drawLine(lastX, lastY, x, y);
        }
    }

    public final void drawGrid() {
        this.setColor(Color.SILVER);
        this.drawGrid(0.5);
        this.setColor(DARK_GRAY);
        this.drawGrid(1.0);
    }

    public final void drawGrid(final double step) {
        double x;
        double y;

        x = 0.0;
        while (x <= this.maxX) {
            x = x + step;
            this.drawLine(x, this.minY, x, this.maxY);
        }

        x = 0.0;
        while (x >= this.minX) {
            x = x - step;
            this.drawLine(x, this.minY, x, this.maxY);
        }

        y = 0.0;
        while (y <= this.maxY) {
            y = y + step;
            this.drawLine(this.minX, y, this.maxX, y);
        }

        y = 0.0;
        while (y >= this.minY) {
            y = y - step;
            this.drawLine(this.minX, y, this.maxX, y);
        }
    }

    public final void drawLine(final double x1, final double y1, final double x2, final double y2) {
        this.window.drawLine(this.screenX(x1), this.screenY(y1), this.screenX(x2), this.screenY(y2));
    }

    public final void drawPoint(final double x, final double y) {
        final double sx = this.screenX(x);
        final double sy = this.screenY(y);
        switch (this.pointShape) {
            case Circle:
                this.window.fillCircle(sx, sy, POINT_RADIUS);
                break;
            case Cross:
                this.window.drawLine(sx - CROSS_SIZE, sy, sx + CROSS_SIZE, sy);
                this.window.drawLine(sx, sy - CROSS_SIZE, sx, sy + CROSS_SIZE);
                break;
        }
    }

    public final void drawPoint(final double x, final double y, final String text) {
        this.window.fillCircle(screenX(x), screenY(y), POINT_RADIUS);
        this.drawText(x, y, text);
    }

    public final void drawText(final double x, final double y, final String text) {
        this.window.drawText(screenX(x) + TEXT_OFFSET, screenY(y) + TEXT_OFFSET, text);
    }

    public final double getMaxX() {
        return this.maxX;
    }

    public final double getMinX() {
        return this.minX;
    }

    public final double getSizeX() {
        return this.maxX - this.minX;
    }

    public final void setColor(final Color color) {
        this.window.setColor(color);
    }

    public final void setLineWidth(final double lineWidth) {
        this.window.setLineWidth(lineWidth);
    }

    public final void setMaxX(final double maxX) {
        this.maxX = maxX;
    }

    public final void setMaxY(final double maxY) {
        this.maxY = maxY;
    }

    public final void setMinX(final double minX) {
        this.minX = minX;
    }

    public final void setMinY(final double minY) {
        this.minY = minY;
    }

    public final void setPointShape(final PointShape pointShape) {
        this.pointShape = pointShape;
    }

    public final void setRange(final double range) {
        this.minX = -range / 2.0;
        this.maxX = range / 2.0;
        this.minY = -range / 2.0;
        this.maxY = range / 2.0;
    }

    public final void setRange(final double minX, final double maxX, final double minY, final double maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public final void setTitle(final String title) {
        this.window.setTitle(title);
    }

    private double screenX(final double x) {
        return ((x - this.minX) / (this.maxX - this.minX)) * this.window.getWidth();
    }

    private double screenY(final double y) {
        return ((y - this.maxY) / (this.minY - this.maxY)) * this.window.getHeight();
    }
}
