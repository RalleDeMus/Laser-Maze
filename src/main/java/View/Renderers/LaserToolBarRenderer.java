package View.Renderers;

import Controller.AssetServer;
import Controller.ImageHandler;
import Model.Logic.Board;
import Model.Logic.LaserCalculator;
import Model.Logic.PointStringPair;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
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

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < board.get_game_info_by_index(i); j++) {
                tiles.add(getTileImage(i));
            }

        }

        for (int i = 0; i < tiles.size(); i++) {
            g.drawImage(tiles.get(i), board.getBoardSize() * board.getSquareSize()+board.getSquareSize()/2, i * board.getSquareSize(), board.getSquareSize(), board.getSquareSize(), null);
        }

    }

    private BufferedImage getTileImage(int i) {

        switch (i) {
            case 0:
                return assetServer.getImage("targetMirror");
            case 1:
                return assetServer.getImage("beamSplitter");
            case 2:
                return assetServer.getImage("checkPoint");
            case 3:
                return assetServer.getImage("doubleMirror");
            case 4:
                return assetServer.getImage("laser");
        }
        return null;
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

                    if (!value.equals("___")) {
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
                    }

                }

            }
            else {
                System.out.println("Not all mirrors used");
            }

        }


    }
}