package ch.jeda.example;

import ch.jeda.Data;
import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class ZeichnenClient extends Program implements PointerDownListener,
                                                       PointerMovedListener,
                                                       TickListener,
                                                       KeyUpListener,
                                                       MessageReceivedListener {

    View fenster;
    StringInputField serverNameInput;
    Button connectButton;
    TcpConnection connection;
    String message;
    Color color;
    int x;
    int y;

    @Override
    public void run() {
        fenster = new View();
        x = fenster.getWidth() / 2;
        y = fenster.getHeight() / 2 - 120;
        serverNameInput = new StringInputField(x, y, Alignment.CENTER);
        fenster.add(serverNameInput);
        y = y + 70;
        connectButton = new Button(x, y, Alignment.CENTER, "Verbinden");
        connectButton.setKey(Key.ENTER);
        connectButton.setStyle(DefaultButtonStyle.MODERN_GREEN);
        y = y + 50;
        fenster.add(connectButton);
        serverNameInput.select();
        fenster.addEventListener(this);
    }

    @Override
    public void onPointerDown(PointerEvent event) {
        draw(event);
    }

    @Override
    public void onPointerMoved(PointerEvent event) {
        draw(event);
    }

    @Override
    public void onTick(TickEvent event) {
        if (connection == null) {
            clear();
            fenster.getBackground().setColor(Color.BLACK);
            fenster.getBackground().drawText(fenster.getWidth() / 2, y, message, Alignment.CENTER);
        }
    }

    @Override
    public void onMessageReceived(MessageEvent event) {
        color = event.getData().readObject("color");
    }

    @Override
    public void onKeyUp(KeyEvent event) {
        if (event.getKey() == Key.ENTER && connection == null) {
            connection = new TcpConnection();
            if (connection.open(serverNameInput.getValue(), 1248)) {
                fenster.remove(serverNameInput);
                fenster.remove(connectButton);
                clear();
            }
            else {
                connection = null;
                message = "Verbindung mit Server nicht möglich.";
                serverNameInput.select();
            }
        }
    }

    private void clear() {
        fenster.getBackground().setColor(Color.WHITE);
        fenster.getBackground().fill();
        JedaLogo.draw(fenster.getBackground());
    }

    private void draw(PointerEvent event) {
        if (connection != null) {
            Data data = new Data();
            data.writeInt("x", event.getX());
            data.writeInt("y", event.getY());
            connection.sendData(data);
            fenster.getBackground().setColor(color);
            fenster.getBackground().fillCircle(event.getX(), event.getY(), 5);
        }
    }

}
