package ch.jeda.example;

import ch.jeda.Data;
import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;
import java.util.ArrayList;
import java.util.List;

public class ZeichnenServer extends Program implements TickListener {

    Window fenster;
    NetworkServer server;
    List<NetworkSocket> sockets;
    List<Color> clientColors;
    List<Color> availableColors;

    @Override
    public void run() {
        sockets = new ArrayList<NetworkSocket>();
        clientColors = new ArrayList<Color>();
        availableColors = new ArrayList<Color>();
        availableColors.add(Color.RED);
        availableColors.add(Color.GREEN);
        availableColors.add(Color.BLUE);
        availableColors.add(Color.BLACK);
        availableColors.add(Color.JEDA);
        server = new NetworkServer();
        server.start(1248);
        fenster = new Window();
        fenster.addEventListener(this);
        JedaLogo.draw(fenster);
    }

    @Override
    public void onTick(TickEvent event) {
        if (server.hasNewConnection() && !availableColors.isEmpty()) {
            NetworkSocket newClient = server.acceptNewConnection();
            Color newColor = availableColors.get(0);
            availableColors.remove(0);
            sockets.add(newClient);
            clientColors.add(newColor);
            Data data = new Data();
            data.writeObject("color", newColor);
            newClient.sendData(data);
        }

        for (int i = 0; i < sockets.size(); ++i) {
            handleClient(sockets.get(i), clientColors.get(i));
        }
    }

    void handleClient(NetworkSocket socket, Color color) {
        if (!socket.isConnected()) {
            sockets.remove(socket);
            availableColors.add(color);
        }

        while (socket.hasData()) {
            Data data = socket.receiveData();
            int x = data.readInt("x", -100);
            int y = data.readInt("y", -100);
            if (x >= 0 && y >= 0) {
                fenster.setColor(color);
                fenster.fillCircle(x, y, 5);
            }
        }
    }
}
