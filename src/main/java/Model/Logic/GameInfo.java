package Model.Logic;

import Model.Tiles.Tile;
import io.cucumber.java.sl.In;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to store the game information.
 */

public class GameInfo {
    private int level;
    private int targets;
    private Map<String, Integer> tiles;

    /**
     * Constructor for the GameInfo class.
     * This class is used to store the game information.
     * @param level level of the game
     * @param targets how many tiles in the game
     * @param tiles a map of the tiles and the number of them
     */
    public GameInfo(int level, int targets, Map<String, Integer> tiles) {
        this.level = level;
        this.targets = targets;
        this.tiles = tiles;
    }

    public int getLevel() {
        return level;
    }

    public int getTargets() {
        return targets;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setTargets(int targets) {
        this.targets = targets;
    }


    public int getTileFromDictionary(String key) {
        if (!tiles.containsKey(key)) {
            return 0;
        }

        return tiles.get(key);
    }

    public Map<String, Integer> getTiles() {
        return tiles;
    }


    public void setTiles(Map<String, Integer> tiles) {
        this.tiles = tiles;
    }

    public void decrementAtKey(String key) {
        tiles.put(key, tiles.get(key) - 1);
    }
    public void incrementAtKey(String key) {
        tiles.put(key, tiles.get(key) + 1);
    }

}
