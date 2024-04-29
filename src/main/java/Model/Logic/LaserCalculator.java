package Model.Logic;

import Model.Tiles.CellBlockerTile;
import Model.Tiles.LaserTile;
import Model.Tiles.SplitterTile;
import Model.Tiles.Tile;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Class for calculating the path of the laser on the board.
 * Only has a big function that calculates the path of the laser.
 * Also contains helper functions for the calculation.
 */
public class LaserCalculator {

    /**
     * Constructs a laser tree from the board.
     * The laser tree is a list of points and strings that represent the path of the laser.
     * Each string is resposible for the direction of the laser at the point: where it comes from and where it goes.
     */
    public static List<PointStringPair> constructLaserTree(Board board) { // REMOVE
        System.out.println("Constructing laser tree");
        if (allMirrorsUsed(board)) {
            // We initialize the variables being used
            int boardSize = board.getBoardSize();
            Tile[][] tiles = board.getTiles();

            int targetsHit = 0;
            int mirrorsHit = 0;

            List<PointStringPair> laserList = new ArrayList<>();

            Tile laserTile = null;

            Laser laser = new Laser(0, 0, 0);

            // Find the laser tile
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

                Queue<Laser> lasers = new LinkedList<>(); // Lasers to be added
                Set<Laser> lasersList = new HashSet<>(); // Lists of the lasers that have been added

                // Add the first laser to the queue to start the calculation
                lasers.add(new Laser(laser.getX() + orientationToPoint(laser.getOrientation()).x, laser.getY() + orientationToPoint(laser.getOrientation()).y, laser.getOrientation()));

                // Laser calculation algorithm
                // We add new lasers to the queue that we need to evaluate
                // If the laser hits a mirror we add a new laser to the queue with the new orientation that makes the laser bend
                // Splitters add two new lasers to the queue
                // Ends when lasers are fired out of the board or hitting a laser that it can't pass through
                while (!lasers.isEmpty()) {
                    Laser current = lasers.remove();

                    // Is the laser out of bounds?
                    if (current.getX() < 0 || current.getX() >= boardSize || current.getY() < 0 || current.getY() >= boardSize) {

                        continue;
                    }

                    // Fromdir and todir handles which direction the laser is coming from and going to
                    String fromDir = String.valueOf(current.getOrientation());
                    String toDir = String.valueOf(current.getOrientation()) + "_";


                    // Is there a tile at the current position?
                    if (tiles[current.getY()][current.getX()] != null) {
                        // Get the tile
                        Tile tile = tiles[current.getY()][current.getX()];
                        if (!(tile instanceof CellBlockerTile)) {
                            // Add to mirrors hit if the tile is not a cell blocker
                            mirrorsHit++;
                        }


                        // Get the corrected way that the laser is facing in respect to the tile
                        int laserCorrected = subMod(tile.getOrientation(), current.getOrientation(), 4);

                        // Check if the tile is a target
                        if (tile.getTarget()[laserCorrected] == 1) {
                            // If so, increment the targets hit
                            targetsHit++;
                            toDir = "8_";
                        }


                        if (tile instanceof SplitterTile) {
                            // If the tile is a splitter, add a new lasers here aswell as the rotated one in a moment.
                            Laser adding = new Laser(current.getX() + orientationToPoint(current.getOrientation()).x, current.getY() + orientationToPoint(current.getOrientation()).y, current.getOrientation());
                            if (canAddLaser(lasersList, adding)) {lasers.add(adding); lasersList.add(adding);}
                        }

                        if (tile.getPass()[laserCorrected] == 0) {
                            // If the laser is not allowed to pass, stop the laser and add no more tiles unless its a target in which case we add a final laser so we can draw the laser hit the target
                            if (tile.getTarget()[laserCorrected] == 0) continue;

                        } else {
                            // If the laser is allowed to pass, add a new laser to the queue
                            int rotateBy = tile.getMirror()[laserCorrected];
                            int nextLaserOrientation = (current.getOrientation() + rotateBy) % 4;

                            Laser adding = new Laser(current.getX() + orientationToPoint(nextLaserOrientation).x, current.getY() + orientationToPoint(nextLaserOrientation).y, nextLaserOrientation);
                            if (canAddLaser(lasersList, adding)) {lasers.add(adding); lasersList.add(adding);}                            //System.out.println("At mirror, adding next: " + adding.toString());

                            toDir = String.valueOf(nextLaserOrientation);
                            toDir += (tile instanceof SplitterTile) ? fromDir : "_";
                        }


                    } else {
                        // Add a next laser to the queue if no tile is found
                        Laser adding = new Laser(current.getX() + orientationToPoint(current.getOrientation()).x, current.getY() + orientationToPoint(current.getOrientation()).y, current.getOrientation());
                        if (canAddLaser(lasersList, adding)) {lasers.add(adding); lasersList.add(adding);}
                    }

                    // Now we add the current laser to the list of lasers to be drawn
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

    /**
     * Helper function to check if a laser can be added to the list of lasers. Only being used if the laser goes in a circle
     */

    private static Boolean canAddLaser(Set<Laser> lasersList, Laser adding) { // REMOVE
        //Don't add if adding is already in the queue
        for (Laser laser : lasersList) {
            if (laser.Equals(adding)){
                System.out.println("Laser already exists");
                return false;
            }
        }
        return true;
    }

    // Regular modulo function that returns a positive number always
    private static int subMod(int a, int b, int mod) { // REMOVE
        return (a - b + mod) % mod;
    }

    // Helper function to convert an orientation to a point
    private static Point orientationToPoint(int orientation) { // REMOVE
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


    // Helper function to check if all mirrors are used
    private static boolean allMirrorsUsed(Board board){ // REMOVE
        int placeabletiles = 0;
        for (int i = 0; i < 4; i++) {
            placeabletiles += board.get_game_info_by_index(i);
        }

        // CHECK FOR MIRRORS THAT ARE ROTATED AND RETURN FALSE IF ANY ARE


        return placeabletiles == 0;
    }
}
