package Model.Logic;

import Model.Tiles.Tile;
import io.cucumber.java.sl.In;

import java.util.HashMap;
import java.util.Map;

public class GameInfo {
    private int level;
    private int targets;
    private Map<String, Integer> tiles;

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


    public void addToTileDictionary(String key, Integer value) {
        tiles.put(key, value);
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

    public void printTiles() {
        System.out.println("Printing tiles dictionary: "+ tiles.size());
        for (Map.Entry<String, Integer> entry : tiles.entrySet()) {
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
        }
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
