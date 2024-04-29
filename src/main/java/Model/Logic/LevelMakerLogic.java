package Model.Logic;

/**
 * Class for handling the level maker logic.
 * The level maker logic is used to keep track of the number of targets and tiles available to the player.
 */
public class LevelMakerLogic {
    private int targets; // The number of targets in the level
    final private int[] tileCounts; // The number of each tile type available to the player


    public LevelMakerLogic() {
        this.targets = 0;
        this.tileCounts = new int[]{0,0,0,0};
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

    public int[] getTileCounts() {
        return tileCounts;
    }


    public void changeTileCount(int tileType, boolean isIncrement) {

        // Get the change in tile count
        int change;
        if(isIncrement){
            change = 1;
        } else {
            change = -1;
        }

        //Make sure the tile count is within the bounds
        int count = 0;
        for(int i = 0; i < 4; i++) {
            count+=tileCounts[i];
        }
        if (count+change > 5){
            return;
        }

        // Make sure the tile count is within the bounds
        if (tileCounts[tileType] + change < 0) {
            return;
        }

        // Only add one laser tile
        if (tileType == 4 && tileCounts[tileType] + change > 1) {
            return;
        }

        // Update the tile count
        tileCounts[tileType] += change;
    }


}
