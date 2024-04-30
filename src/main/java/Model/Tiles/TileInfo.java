package Model.Tiles;

import Model.Tiles.GameTiles.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Constructor;

import org.reflections.Reflections;
import java.util.Set;
import Model.Tiles.Tile;


/**
 * Class to get information about the tiles.
 * This class is used to get the tiles from the package and create instances of them.
 */

public class TileInfo {

    public static Tile TileFromKey(String className) {
        Reflections reflections = new Reflections("Model.Tiles.GameTiles");
        Set<Class<? extends Tile>> tileClasses = reflections.getSubTypesOf(Tile.class);

        for (Class<? extends Tile> tileClass : tileClasses) {
            if (tileClass.getSimpleName().equals(className)) {
                try {
                    Tile tileInstance = null;

                    // Check if there is a no-argument constructor
                    try {
                        Constructor<? extends Tile> constructor = tileClass.getDeclaredConstructor();
                        tileInstance = constructor.newInstance();
                    } catch (NoSuchMethodException e) {
                        // No no-arg constructor, try with two boolean args
                        Constructor<? extends Tile> constructor = tileClass.getDeclaredConstructor(boolean.class, boolean.class);
                        tileInstance = constructor.newInstance(true, true);  // Assuming all need true, true
                    }

                    return tileInstance;

                } catch (Exception e) {
                }
            }
        }

        return null;
    }

    public static List<Tile> getTiles(boolean checkIsMirror) {
        List<Tile> tiles = new ArrayList<>();
        Reflections reflections = new Reflections("Model.Tiles.GameTiles");

        Set<Class<? extends Tile>> tileClasses = reflections.getSubTypesOf(Tile.class);

        for (Class<? extends Tile> tileClass : tileClasses) {
            try {
                Tile tileInstance;

                // Check if there is a no-argument constructor
                try {
                    Constructor<? extends Tile> constructor = tileClass.getDeclaredConstructor();
                    tileInstance = constructor.newInstance();
                } catch (NoSuchMethodException e) {
                    // No no-arg constructor, try with two boolean args
                    Constructor<? extends Tile> constructor = tileClass.getDeclaredConstructor(boolean.class, boolean.class);
                    tileInstance = constructor.newInstance(true, true);  // Assuming all need true, true
                }

                tiles.add(tileInstance);
            } catch (Exception e) {
                throw new AssertionError(e);
            }
        }

        if (checkIsMirror) {
            tiles.removeIf(tile -> !tile.getIsMirror());
        }

        return tiles;
    }

}
