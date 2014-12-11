package ch.jeda.pong;

import ch.jeda.Data;
import ch.jeda.NetworkSocket;
import ch.jeda.event.TickEvent;
import ch.jeda.event.TickListener;

public class PaddleControlClient implements TickListener {

    private PaddleControl control;
    private NetworkSocket socket;

    public PaddleControlClient(String server, PaddleControl control) {
        socket = new NetworkSocket();
        socket.connect(server, 2222);
        this.control = control;
    }

    @Override
    public void onTick(TickEvent event) {
        if (control.shouldMove()) {
            Data data = new Data();
            data.writeInt("y", control.getTargetPos());
            socket.sendData(data);
        }
    }
}
