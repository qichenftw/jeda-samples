package ch.jeda.example.cutemaze;

import ch.jeda.*;
import ch.jeda.cute.*;
import ch.jeda.event.*;
import ch.jeda.ui.*;

public class CuteMaze extends Program implements TickListener, KeyDownListener {

    Window window;
    CuteWorld world;
    CuteSprite player;

    @Override
    public void run() {
        window = new Window(WindowFeature.DOUBLE_BUFFERED);
        // Welt erstellen
        world = new CuteWorld(40, 40, 4);
        world.fill(0, Block.GRASS);
        for (int x = 0; x < world.getSizeX(); ++x) {
            world.setBlockAt(x, 0, 1, Block.STONE);
            world.setBlockAt(x, world.getSizeY() - 1, 1, Block.STONE);
        }

        for (int y = 0; y < world.getSizeY(); ++y) {
            world.setBlockAt(0, y, 1, Block.STONE);
            world.setBlockAt(world.getSizeX() - 1, y, 1, Block.STONE);
        }

        world.addObject(new CuteObject(CuteObjectType.STAR, 4, 1, 1));
        player = new CuteSprite(CuteObjectType.CAT_GIRL, 1, 1, 1);
        world.addObject(player);
        world.setScrollLock(player);

        window.addEventListener(this);
    }

    @Override
    public void onTick(TickEvent event) {
        // Welt aktualisieren
        world.update(event.getDuration());
        // Himmel zeichnen
        window.setColor(Color.BLUE);
        window.fill();
        // Welt zeichnen
        world.draw(window);
    }

    @Override
    public void onKeyDown(KeyEvent event) {
        switch (event.getKey()) {
            case UP:
                player.move(Direction.NORTH);
                break;
            case DOWN:
                player.move(Direction.SOUTH);
                break;
            case LEFT:
                player.move(Direction.WEST, world.getHeightAt(0, 0));
                break;
            case RIGHT:
                player.move(Direction.EAST);
                break;
        }
    }
}
