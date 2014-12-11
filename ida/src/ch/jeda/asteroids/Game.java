package ch.jeda.asteroids;

import ch.jeda.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class Game extends Program implements ActionListener, TickListener, KeyUpListener {

    private static final String[] ABOUT = {
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
    private Window window;
    private Image menuBackground;
    private int asteroidCount = 2;
    private Spaceship spaceship;
    private int score;
    private PressedKeys pressedKeys;
    private Composite mainMenu;
    private Music menuMusic;
    private Composite about;
    private HUD hud;

    @Override
    public void run() {
        window = new Window(1024, 768, WindowFeature.DOUBLE_BUFFERED);
        window.setAntiAliasing(true);
        pressedKeys = new PressedKeys();
        window.addEventListener(pressedKeys);

        hud = new HUD();
        menuBackground = new Image("res:drawable/background-menu.jpg");
        initMenu();
        initAbout();
        menuMusic.play();
        window.add(mainMenu);
        window.addEventListener(this);

    }

    @Override
    public void onAction(ActionEvent event) {
        if ("Quit".equals(event.getName())) {
            menuMusic.stop();
            window.close();
        }
        else if ("About".equals(event.getName())) {
            mainMenu.setVisible(false);
            about.setVisible(true);
        }
        else if ("Start Game".equals(event.getName())) {
            menuMusic.stop();
            mainMenu.setVisible(false);
            startGame();
        }
    }

    @Override
    public void onKeyUp(KeyEvent event) {
        // Cheat for testing
        if (event.getKey() == Key.P) {
            for (Asteroid asteroid : window.getElements(Asteroid.class)) {
                window.remove(asteroid);
            }
        }
    }

    @Override
    public void onTick(TickEvent event) {
        if (spaceship != null && window.getElements(Asteroid.class).length == 0) {
            asteroidCount = asteroidCount + 2;
            addAsteroids(asteroidCount);
        }

        window.drawImage(0, 0, menuBackground);
    }

    int getSpaceHeight() {
        return window.getHeight();
    }

    int getSpaceWidth() {
        return window.getWidth();
    }

    void addScore(int amount) {
        score = score + amount;
        hud.setScore(this.score);
    }

    private void initAbout() {
        about = new Composite();
        window.add(about);
        about.add(new ImageWidget(0, 0, menuBackground));
        about.add(new ScrollingText(ABOUT));
        about.setVisible(false);
    }

    private void initMenu() {
        menuMusic = new Music("res:raw/glowsphere.mp3");
        mainMenu = new Composite();
        mainMenu.setName("Main Menu");
        Menu menu = new Menu(window.getWidth() / 2, 300, Alignment.CENTER);
        menu.addOption("Start Game");
        menu.addOption("About");
        menu.addOption("Quit");
        mainMenu.add(new ImageWidget(0, 0, menuBackground));
        mainMenu.add(menu);
        window.add(mainMenu);
    }

    private void startGame() {
        spaceship = new Spaceship(window.getWidth() / 2, window.getHeight() / 2);
        window.add(spaceship);
        window.add(hud);
        asteroidCount = 0;
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
