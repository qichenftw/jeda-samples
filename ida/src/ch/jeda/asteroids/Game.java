package ch.jeda.asteroids;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class Game extends Program implements ActionListener, TickListener, KeyUpListener {

    private static String[] ABOUT = {
        "Ida",
        "",
        "by Stefan Rothe",
        "",
        "This is an Asteroid-like",
        "cooperative game",
        "programmed in Java",
        "using the Jeda game library",
        "http://jeda.ch",
        "",
        "All graphics, sounds and music",
        "used in this game are",
        "dedicated to the public domain",
        "and are available on",
        "the Jeda website."
    };
    private static Color DISPLAY_BG = new Color(100, 155, 100, 150);
    private static Color DISPLAY_FG = new Color(100, 255, 100);
    private Window window;
    private Image background;
    private int asteroidCount = 2;
    private Spaceship spaceship;
    private int score;
    private PressedKeys pressedKeys;
    private Menu menu;
    private Music menuMusic;
    private ScrollingText aboutText;

    @Override
    public void run() {
        window = new Window(1024, 768, WindowFeature.DOUBLE_BUFFERED);
        window.setAntiAliasing(true);
        pressedKeys = new PressedKeys();
        window.addEventListener(pressedKeys);

        background = new Image("res:drawable/background-menu.jpg");
        initMenu();
        menuMusic.play();
        window.addEventListener(this);

        aboutText = new ScrollingText(ABOUT);
    }

    @Override
    public void onAction(ActionEvent event) {
        if ("Quit".equals(event.getName())) {
            menuMusic.stop();
            window.close();
        }
        else if ("About".equals(event.getName())) {
            window.remove(menu);
            window.add(aboutText);
        }
        else if ("Menu".equals(event.getName())) {
            window.remove(aboutText);
            window.add(menu);
        }
    }

    @Override
    public void onKeyUp(KeyEvent event) {
        // Cheat for testing
        if (event.getKey() == Key.P) {
            for (Asteroid asteroid : window.getGraphicsItems(Asteroid.class)) {
                window.remove(asteroid);
            }
        }
    }

    @Override
    public void onTick(TickEvent event) {

//        spaceship.steer(pressedKeys);
//        if (window.getGraphicsItems(Asteroid.class).length == 0) {
//            asteroidCount = asteroidCount + 2;
//            addAsteroids(asteroidCount);
//        }
        window.drawImage(0, 0, background);
        window.setColor(DISPLAY_BG);
        window.fillRectangle(5, 5, 150, 40);
        window.setColor(DISPLAY_FG);
        window.drawRectangle(5, 5, 150, 40);
        window.setFontSize(30);
        window.drawText(10, 10, "Score: " + score);
    }

    int getSpaceHeight() {
        return window.getHeight();
    }

    int getSpaceWidth() {
        return window.getWidth();
    }

    void addScore(int amount) {
        score = score + amount;
    }

    private void initMenu() {
        menuMusic = new Music("res:raw/glowsphere.mp3");
        menu = new Menu(window.getWidth() / 2, 300, Alignment.CENTER);
        menu.addOption("Start Game");
        menu.addOption("About");
        menu.addOption("Quit");
        window.add(menu);

    }

    private void startGame() {
        spaceship = new Spaceship(window.getWidth() / 2, window.getHeight() / 2);
        window.add(spaceship);
        asteroidCount = 2;
        addAsteroids(asteroidCount);
    }

    private void addAsteroids(int count) {
        while (count > 0) {
            int x = Util.randomInt(window.getWidth());
            int y = Util.randomInt(window.getHeight());
            double dx = x - spaceship.getX();
            double dy = y - spaceship.getY();
            // Abstand Asteroid - Raumschiff mindestens 150
            if (dx * dx + dy * dy > 150 * 150) {
                window.add(new Asteroid(this, x, y));
                count = count - 1;
            }
        }
    }
}
