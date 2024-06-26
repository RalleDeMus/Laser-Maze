package View.Renderers;

import Controller.AssetServer;
import Controller.ImageHandler;
import Model.Logic.Board;
import Model.Logic.LaserCalculator;
import Model.Logic.PointStringPair;
import Model.Tiles.Tile;
import Model.Tiles.TileInfo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
    * The LaserToolBarRenderer class is responsible for drawing the laser and the toolbar.

 */
public class LaserToolBarRenderer extends BoardRenderer {

    final private AssetServer assetServer = AssetServer.getInstance();

    public LaserToolBarRenderer(Board board) {
        super(board);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawLaser(g);
        drawToolBar(g);
    }

    private void drawToolBar(Graphics g) {

        List<BufferedImage> tiles = new ArrayList<>();

        if (board.laserNeeded()) {
            tiles.add(assetServer.getImage("laser"));
        }


        List<Tile> tilesWithIsMirror = TileInfo.getTiles(true);

        for (int i = 0; i < tilesWithIsMirror.size(); i++) {
            for (int j = 0; j < board.get_game_info().getTileFromDictionary(tilesWithIsMirror.get(i).getClass().getSimpleName()); j++) {
                tiles.add(Objects.requireNonNull(TileInfo.TileFromKey(tilesWithIsMirror.get(i).getClass().getSimpleName())).getImage());

            }

        }

        int toolbarSize = board.getSquareSize()/10*8;
        for (int i = 0; i < tiles.size(); i++) {
            g.drawImage(tiles.get(i), board.getBoardSize() * board.getSquareSize()+board.getSquareSize()/2, i * toolbarSize, toolbarSize, toolbarSize, null);
        }

    }


    // draw the laserRay
    private void drawLaser(Graphics g){

        if(board.getLaserFired()) {
            List<PointStringPair> laserMap = LaserCalculator.constructLaserTree(board);

            if (laserMap != null) {



                for (PointStringPair pair : laserMap) {
                    int j = pair.getPoint().x;
                    int i = pair.getPoint().y;
                    int squareSize = board.getSquareSize();

                    String value = pair.getValue();

                    if (!value.equals("____")) {
                        BufferedImage image = assetServer.getImage("laserRay");

                        if (value.charAt(0) != '_') {
                            if (value.charAt(1) == '8') {
                                int direction = Character.getNumericValue(value.charAt(0));

                                g.drawImage(ImageHandler.rotateImage(assetServer.getImage("laserRayTarget"), 90 * direction), j * squareSize, i * squareSize, squareSize, squareSize, null);

                            } else {
                                int direction = Character.getNumericValue(value.charAt(0));
                                g.drawImage(ImageHandler.rotateImage(image, 90 * direction), j * squareSize, i * squareSize, squareSize, squareSize, null);
                            }
                        }

                        if (value.charAt(1) != '_') {
                            if (value.charAt(1) != '8') {
                                int direction = Character.getNumericValue(value.charAt(1)) + 2;

                                g.drawImage(ImageHandler.rotateImage(image, 90 * direction), j * squareSize, i * squareSize, squareSize, squareSize, null);

                            }
                        }


                        if (value.charAt(2) != '_') {
                            int direction = Character.getNumericValue(value.charAt(2)) + 2;
                            g.drawImage(ImageHandler.rotateImage(image, 90 * direction), j * squareSize, i * squareSize, squareSize, squareSize, null);
                        }
                        if (value.charAt(3) != '_') {
                            int direction = Character.getNumericValue(value.charAt(3)) + 2;
                            g.drawImage(ImageHandler.rotateImage(image, 90 * direction), j * squareSize, i * squareSize, squareSize, squareSize, null);
                        }
                    }

                }

            }
            else {
                System.out.println("Not all mirrors used");
            }

        }


    }
}