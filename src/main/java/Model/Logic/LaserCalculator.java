package Model.Logic;

import Model.Tiles.CellBlockerTile;
import Model.Tiles.LaserTile;
import Model.Tiles.SplitterTile;
import Model.Tiles.Tile;

import java.awt.*;
import java.util.*;
import java.util.List;

public class LaserCalculator {
    public static List<PointStringPair> constructLaserTree(Board board) { // REMOVE
        System.out.println("Constructing laser tree");
        if (allMirrorsUsed(board)) {
            int boardSize = board.getBoardSize();
            Tile[][] tiles = board.getTiles();

            int targetsHit = 0;
            int mirrorsHit = 0;

            List<PointStringPair> laserList = new ArrayList<>();

            Tile laserTile = null;

            Laser laser = new Laser(0, 0, 0);

            for (int row = 0; row < boardSize; row++) {
                for (int col = 0; col < boardSize; col++) {
                    if (tiles[row][col] != null && tiles[row][col] instanceof LaserTile) {
                        laserTile = tiles[row][col];
                        laser.setPos(col, row);
                        laser.setOrientation(laserTile.getOrientation());

                    }

                }
            }

            if (laserTile != null) {

                Queue<Laser> lasers = new LinkedList<>();
                Set<Laser> lasersList = new HashSet<>();



                List<Laser> hitLasers = new LinkedList<>();
                lasers.add(new Laser(laser.getX() + orientationToPoint(laser.getOrientation()).x, laser.getY() + orientationToPoint(laser.getOrientation()).y, laser.getOrientation()));

                mirrorsHit = 0;

                while (!lasers.isEmpty()) {
                    Laser current = lasers.remove();

                    // Is the laser out of bounds?
                    if (current.getX() < 0 || current.getX() >= boardSize || current.getY() < 0 || current.getY() >= boardSize) {

                        //System.out.println("Laser out of bounds");
                        continue;
                    }
                    hitLasers.add(current);

                    String fromDir = String.valueOf(current.getOrientation());
                    String toDir = String.valueOf(current.getOrientation()) + "_";

                    // Set the current as a hit on laserHasHit
                    //System.out.println("Laser at: " + current.getX() + " " + current.getY() + " orientation: " + current.getOrientation();

                    // Is there a tile at the current position?
                    if (tiles[current.getY()][current.getX()] != null) {
                        // Get the tile
                        Tile tile = tiles[current.getY()][current.getX()];
                        if (!(tile instanceof CellBlockerTile)) {
                            mirrorsHit++;
                        }


                        // Get the corrected way that the laser is facing in respect to the tile
                        int laserCorrected = subMod(tile.getOrientation(), current.getOrientation(), 4);

                        // Check if the tile is a target
                        if (tile.getTarget()[laserCorrected] == 1) {
                            // If so, increment the targets hit
                            //System.out.println("Target HIT: " + laserCorrected + " gettarget: " + Arrays.toString(tile.getTarget()));
                            targetsHit++;
                            toDir = "8_";
                        }


                        if (tile instanceof SplitterTile) {
                            // If the tile is a splitter, add a new lasers here aswell as the rotated one!
                            Laser adding = new Laser(current.getX() + orientationToPoint(current.getOrientation()).x, current.getY() + orientationToPoint(current.getOrientation()).y, current.getOrientation());
                            if (canAddLaser(lasersList, adding)) {lasers.add(adding); lasersList.add(adding);}
                        }

                        if (tile.getPass()[laserCorrected] == 0) {
                            // If the laser is not allowed to pass, stop the laser and add no more tiles
                            //System.out.println("Mirror? " + tile);
                            //System.out.println("Mirror blocked: " + Arrays.toString(tile.getPass()));
                            if (tile.getTarget()[laserCorrected] == 0) continue;

                        } else {

                            //System.out.println("Evaluating tile: " + "\n" + laserCorrected);
                            int rotateBy = tile.getMirror()[laserCorrected];
                            int nextLaserOrientation = (current.getOrientation() + rotateBy) % 4;

                            //System.out.println(rotateBy);
                            //System.out.println(nextLaserOrientation);
                            Laser adding = new Laser(current.getX() + orientationToPoint(nextLaserOrientation).x, current.getY() + orientationToPoint(nextLaserOrientation).y, nextLaserOrientation);
                            if (canAddLaser(lasersList, adding)) {lasers.add(adding); lasersList.add(adding);}                            //System.out.println("At mirror, adding next: " + adding.toString());

                            toDir = String.valueOf(nextLaserOrientation);
                            toDir += (tile instanceof SplitterTile) ? fromDir : "_";
                        }


                    } else {
                        // Add a next laser to the queue if no tile is found
                        Laser adding = new Laser(current.getX() + orientationToPoint(current.getOrientation()).x, current.getY() + orientationToPoint(current.getOrientation()).y, current.getOrientation());
                        if (canAddLaser(lasersList, adding)) {lasers.add(adding); lasersList.add(adding);}


                        //System.out.println("At empty tile, adding next: " + adding.toString());
                    }

                    laserList.add(new PointStringPair(new Point(current.getX(), current.getY()), String.valueOf(fromDir) + String.valueOf(toDir)));


                }

            }

            board.setMirrorsHit(mirrorsHit);
            board.setTargetsHit(targetsHit);

            board.setWin();
            return laserList;
        } else {

            return null;
        }


    }

    static Boolean canAddLaser(Set<Laser> lasersList, Laser adding) { // REMOVE
        //Don't add if adding is already in the queue
        for (Laser laser : lasersList) {
            if (laser.Equals(adding)){
                System.out.println("Laser already exists");
                return false;
            }
        }
        return true;
    }

    static int subMod(int a, int b, int mod) { // REMOVE
        return (a - b + mod) % mod;
    }

    static Point orientationToPoint(int orientation) { // REMOVE
        switch (orientation) {
            case 1:
                return new Point(0, 1); // Up
            case 0:
                return new Point(1, 0); // Right
            case 3:
                return new Point(0, -1); // Down
            case 2:
                return new Point(-1, 0); // Left
            default:
                throw new IllegalArgumentException("Invalid orientation: " + orientation);
        }
    }


    static boolean allMirrorsUsed(Board board){ // REMOVE
        int placeabletiles = 0;
        for (int i = 0; i < 4; i++) {
            placeabletiles += board.get_game_info_by_index(i);
        }

        // CHECK FOR MIRRORS THAT ARE ROTATED AND RETURN FALSE IF ANY ARE


        return placeabletiles == 0;
    }
}
