import Tiles.*;
 class Card {
     private int level;
     private int targetMirrorTiles;
     private int splitterTiles;
     private int checkPointTiles;
     private int doubleTiles;
     private int cellBlockerTiles;
     private Tile[][] tiles = new Tile[5][5];;
     public Card(int level){
         this.level = level;

         //load json file
            //parse json file

         //initialize tiles
         for (int row = 0; row < 5; row++) {
             for (int col = 0; col < 5; col++) {
                 this.tiles[row][col] = null;

             }
         }
         //add json implementation

     }

     public Tile[][] getCard(){
         if (level == 1){
             targetMirrorTiles = 1;
             splitterTiles = 0;
             checkPointTiles = 0;
             doubleTiles = 1;
             cellBlockerTiles = 0;


             this.tiles[0][0] = new LaserTile(false,true);
             this.tiles[0][3] = new CellBlockerTile();
             this.tiles[3][2] = new CheckPointTile(false,true);
             this.tiles[3][3] = new SplitterTile(false,true);
             this.tiles[4][3] = new MirrorTile(false,true);
         }
         return this.tiles;
     }

     public int[] getPlaceableTiles(){
         int[] placeableTiles = new int[5];
         placeableTiles[0] = targetMirrorTiles;
         placeableTiles[1] = splitterTiles;
         placeableTiles[2] = checkPointTiles;
         placeableTiles[3] = doubleTiles;
         placeableTiles[4] = cellBlockerTiles;
         return placeableTiles;
     }
}
