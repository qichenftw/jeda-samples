package ch.jeda.sample;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class NetTicTacToe extends Program implements TickListener, PointerDownListener {

    private static final int PORT = 1248;
    private Window window;
    private NetworkServer server;
    private NetworkSocket socket;

    @Override
    public void run() {
        window = new Window();
        server = new NetworkServer();
        socket = new NetworkSocket();
        while (!server.isRunning() && socket.isConnected()) {
            String host = readString("Server (leer um Server lokal zu starten): ");
            if (host.isEmpty()) {
                server.start(PORT);
            }
            else {
                socket.connect(host, PORT);
            }
        }

        if (server.isRunning()) {
            socket = null;
        }
    }

    @Override
    public void onTick(TickEvent event) {
        if (socket == null && server.hasNewConnection()) {
            socket = server.acceptNewConnection();
        }

    }

    @Override
    public void onPointerDown(PointerEvent event) {
    }

}
