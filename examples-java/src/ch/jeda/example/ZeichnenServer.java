package ch.jeda.example;

import ch.jeda.Data;
import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZeichnenServer extends Program implements ServerListener {

    Window fenster;
    TcpServer server;
    List<Connection> connections;
    Map<Connection, Color> clientColors;
    List<Color> availableColors;

    @Override
    public void run() {
        connections = new ArrayList<Connection>();
        clientColors = new HashMap<Connection, Color>();
        availableColors = new ArrayList<Color>();
        availableColors.add(Color.RED);
        availableColors.add(Color.GREEN);
        availableColors.add(Color.BLUE);
        availableColors.add(Color.BLACK);
        availableColors.add(Color.JEDA);
        server = new TcpServer();
        server.start(1248);
        fenster = new Window();
        fenster.addEventListener(this);
        JedaLogo.draw(fenster);
    }

    @Override
    public void onConnectionAccepted(ConnectionEvent event) {
        Connection newConnection = event.getConnection();
        Color newColor = availableColors.get(0);
        availableColors.remove(0);
        connections.add(newConnection);
        clientColors.put(newConnection, newColor);
        Data data = new Data();
        data.writeObject("color", newColor);
        newConnection.sendData(data);
    }

    @Override
    public void onConnectionClosed(ConnectionEvent event) {
        connections.remove(event.getConnection());
        availableColors.add(clientColors.get(event.getConnection()));
        clientColors.remove(event.getConnection());
    }

    @Override
    public void onMessageReceived(MessageEvent event) {
        int x = event.getData().readInt("x", -100);
        int y = event.getData().readInt("y", -100);
        Color color = clientColors.get(event.getConnection());
        if (x >= 0 && y >= 0) {
            fenster.setColor(color);
            fenster.fillCircle(x, y, 5);
        }
    }
}
