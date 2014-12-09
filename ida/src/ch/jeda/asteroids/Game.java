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
    private Window view;
    private Image menuBackground;
    private int asteroidCount = 2;
    private Spaceship spaceship;
    private int score;
    private PressedKeys pressedKeys;
    private Menu menu;
    private Music menuMusic;
    private ScrollingText aboutText;
    private HUD hud;

    @Override
    public void run() {
        view = new Window(1024, 768, WindowFeature.DOUBLE_BUFFERED);
        view.setAntiAliasing(true);
        pressedKeys = new PressedKeys();
        view.addEventListener(pressedKeys);

        hud = new HUD();
        menuBackground = new Image("res:drawable/background-menu.jpg");
        initMenu();
        initAbout();
        menuMusic.play();
        view.setPage("Menu");
        view.addEventListener(this);

    }

    @Override
    public void onAction(ActionEvent event) {
        if ("Quit".equals(event.getName())) {
            menuMusic.stop();
            view.close();
        }
        else if ("About".equals(event.getName())) {
            view.setPage("About");
        }
        else if ("Start Game".equals(event.getName())) {
            menuMusic.stop();
            startGame();
        }
    }

    @Override
    public void onKeyUp(KeyEvent event) {
        // Cheat for testing
        if (event.getKey() == Key.P) {
            for (Asteroid asteroid : view.getElements(Asteroid.class)) {
                view.remove(asteroid);
            }
        }
    }

    @Override
    public void onTick(TickEvent event) {
        if (view.getPage().equals("Game") && view.getElements(Asteroid.class).length == 0) {
            asteroidCount = asteroidCount + 2;
            addAsteroids(asteroidCount);
        }

        view.drawImage(0, 0, menuBackground);
    }

    int getSpaceHeight() {
        return view.getHeight();
    }

    int getSpaceWidth() {
        return view.getWidth();
    }

    void addScore(int amount) {
        score = score + amount;
        hud.setScore(this.score);
    }

    private void initAbout() {
        view.setPage("About");
        view.add(new ImageWidget(0, 0, menuBackground));
        aboutText = new ScrollingText(ABOUT);
        view.add(aboutText);
    }

    private void initMenu() {
        view.setPage("Menu");
        view.add(new ImageWidget(0, 0, menuBackground));
        menuMusic = new Music("res:raw/glowsphere.mp3");
        menu = new Menu(view.getWidth() / 2, 300, Alignment.CENTER);
        menu.addOption("Start Game");
        menu.addOption("About");
        menu.addOption("Quit");
        view.add(menu);
    }

    private void startGame() {
        view.setPage("Game");
        spaceship = new Spaceship(view.getWidth() / 2, view.getHeight() / 2);
        view.add(spaceship);
        view.add(hud);
        asteroidCount = 0;
    }

    private void addAsteroids(int count) {
        while (count > 0) {
            int x = Util.randomInt(view.getWidth());
            int y = Util.randomInt(view.getHeight());
            double dx = x - spaceship.getX();
            double dy = y - spaceship.getY();
            // Abstand Asteroid - Raumschiff mindestens 150
            if (dx * dx + dy * dy > 150 * 150) {
                view.add(new Asteroid(this, x, y));
                count = count - 1;
            }
        }
    }
}
