package ch.jeda.pong;

import ch.jeda.event.PointerEvent;
import ch.jeda.event.PointerListener;

public class PointerPaddleControl extends PaddleControl implements PointerListener {

    private PointerEvent pointer;
    private int minX;
    private int maxX;

    PointerPaddleControl(int minX, int maxX) {
        this.minX = minX;
        this.maxX = maxX;
    }

    @Override
    public boolean shouldMove() {
        return pointer != null;
    }

    @Override
    public int getTargetPos() {
        if (pointer == null) {
            return 0;
        }
        else {
            return pointer.getY();
        }
    }

    @Override
    public void onPointerDown(PointerEvent event) {
        if (pointer == null && minX < event.getX() && event.getX() < maxX) {
            pointer = event;
        }
    }

    @Override
    public void onPointerMoved(PointerEvent event) {
        if (pointer != null && event.getPointerId() == pointer.getPointerId()) {
            pointer = event;
        }
    }

    @Override
    public void onPointerUp(PointerEvent event) {
        if (pointer != null && event.getPointerId() == pointer.getPointerId()) {
            pointer = null;
        }
    }

}
