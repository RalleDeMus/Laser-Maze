package Model.Logic;

public class GameInfo {
    private int level;
    private int targets;
    private int[] tiles;

    public GameInfo(int level, int targets, int[] tiles) {
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

    public int[] getTiles() {
        return tiles;
    }

    public int getTileAtIndex(int index) {
        return tiles[index];
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setTargets(int targets) {
        this.targets = targets;
    }

    public void setTiles(int[] tiles) {
        this.tiles = tiles;
    }

    public void decrementTileAtIndex(int index) {
        tiles[index]--;
    }
    public void incrementTileAtIndex(int index) {
        tiles[index]--;
    }

}
