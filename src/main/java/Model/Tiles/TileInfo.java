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




public class TileInfo {
    // Dictionary containing the ID of each tile
    // 0: MirrorTile
    // 1: SplitterTile
    // 2: CheckPointTile
    // 3: DoubleTile


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
                    //System.err.println("Error creating instance of " + tileClass.getSimpleName() + ": " + e);
                }
            }
        }

        //System.err.println("No class found with the name: " + className);
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
                //System.out.println("Loaded tile class: " + tileClass.getSimpleName());
            } catch (Exception e) {
                throw new AssertionError(e);
                //System.err.println("Error creating instance of " + tileClass.getSimpleName() + ": " + e);
            }
        }

        if (checkIsMirror) {
            tiles.removeIf(tile -> !tile.getIsMirror());
        }

        return tiles;
    }

}
