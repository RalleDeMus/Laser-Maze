package Model.Logic;

import Model.Tiles.TileInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class for handling the level maker logic.
 * The level maker logic is used to keep track of the number of targets and tiles available to the player.
 */
public class LevelMakerLogic {
    private int targets; // The number of targets in the level
    final private Map<String,Integer> tileCounts; // The number of each tile type available to the player


    public LevelMakerLogic() {
        this.targets = 0;
        this.tileCounts = new HashMap<>();
        TileInfo.getTiles(true).forEach(tile -> {
            tileCounts.put(tile.getClass().getSimpleName(), 0);
        });
    }

    public int getTargets() {
        return targets;
    }
    public void setTargets(int targets) {
        this.targets = targets;
    }
    public void decrementTargets(){
        if (targets == 0) return;
        targets--;
    }
    public void incrementTargets(){
        targets++;
    }

    public Map<String, Integer> getTileCounts() {
        return tileCounts;
    }

    /**
     * Changes the number of a specific tile type available to the player.
     * @param tileType the type of tile to change
     * @param isIncrement whether to increment or decrement the tile count
     */
    public void changeTileCount(String tileType, boolean isIncrement) {

        // Get the change in tile count
        int change;
        if(isIncrement){
            change = 1;
        } else {
            change = -1;
        }

        //Make sure the tile count is within the bounds
        final int[] count = {0};
        tileCounts.forEach((key, value) -> count[0]+=value);

        if (count[0]+change > 5){
            return;
        }

        // Make sure the tile count is within the bounds
        if (tileCounts.get(tileType) + change < 0) {
            return;
        }

        // Only add one laser tile
        if (Objects.equals(tileType, "LaserTile") && tileCounts.get(tileType) + change > 1) {
            return;
        }

        // Update the tile count
        tileCounts.put(tileType, tileCounts.get(tileType)+change);
    }


}
