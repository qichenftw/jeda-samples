package ch.jeda.pong;

import ch.jeda.Data;
import ch.jeda.NetworkServer;
import ch.jeda.NetworkSocket;
import ch.jeda.event.TickEvent;
import ch.jeda.event.TickListener;

public class RemotePaddleControl extends PaddleControl implements TickListener {

    private NetworkServer server;
    private NetworkSocket socket;
    private int targetPos;
    private boolean shouldMove;

    public RemotePaddleControl() {
        server = new NetworkServer();
        server.start(2222);
    }

    @Override
    public boolean shouldMove() {
        return shouldMove;
    }

    @Override
    public int getTargetPos() {
        return targetPos;
    }

    @Override
    public void onTick(TickEvent event) {
        if (server.hasNewConnection() && socket == null) {
            socket = server.acceptNewConnection();
        }

        if (socket != null && socket.hasData()) {
            Data data = socket.receiveData();
            targetPos = data.readInt("y");
        }
        else {
            shouldMove = false;
        }
    }

}
