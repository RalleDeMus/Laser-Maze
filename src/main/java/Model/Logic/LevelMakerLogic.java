package Model.Logic;

public class LevelMakerLogic {

    private int targets;
    private int[] tileCounts;

    public LevelMakerLogic(int targets, int[] tileCounts) {
        this.targets = targets;
        this.tileCounts = tileCounts;
    }

    public int getTargets() {
        return targets;
    }
    public void setTargets(int targets) {
        this.targets = targets;
    }

    public int[] getTileCounts() {
        return tileCounts;
    }
    public void setTileCounts(int[] tileCounts) {
        this.tileCounts = tileCounts;
    }

    public void changeTileCount(int tileType, int change) {
        int count = 0;
        for(int i = 0; i < 4; i++) {
            count+=tileCounts[i];
        }
        if (count+change > 5){
            return;
        }

        if (tileCounts[tileType] + change < 0) {
            return;
        }
        if (tileType == 4 && tileCounts[tileType] + change > 1) {
            return;
        }
        tileCounts[tileType] += change;
    }


}
