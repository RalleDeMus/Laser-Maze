package Model.Logic;

public class LevelMakerLogic {

    private int targets;
    private int[] tileCounts;

    public LevelMakerLogic(int targets, int[] tileCounts) {
        this.targets = targets;
        this.tileCounts = tileCounts;
    }

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
    public void setTileCounts(int[] tileCounts) {
        this.tileCounts = tileCounts;
    }

    public void changeTileCount(int tileType, boolean isIncrement) {
        int count = 0;
        int change;
        if(isIncrement){
            change = 1;
        } else {
            change = -1;
        }

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
